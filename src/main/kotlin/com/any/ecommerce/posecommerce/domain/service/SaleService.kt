package com.any.ecommerce.posecommerce.domain.service

import com.any.ecommerce.posecommerce.application.request.TransactionRequest
import com.any.ecommerce.posecommerce.application.response.SalesResponse
import com.any.ecommerce.posecommerce.application.response.TransactionResponse
import com.any.ecommerce.posecommerce.domain.dto.SaleDto
import com.any.ecommerce.posecommerce.infrastructure.entities.SaleEntity
import com.any.ecommerce.posecommerce.infrastructure.entities.enum.Method
import com.any.ecommerce.posecommerce.infrastructure.repository.SaleRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

/**
 * Service for Sale
 */
@Service
class SaleService(
    private val saleRepository: SaleRepository
) {
    // Add sale to the database using the transaction information
    fun addSale(transactionRequest: TransactionRequest, transactionId: String): TransactionResponse {
        val saleEntity = calculateSale(transactionRequest, transactionId)
        saleRepository.save(saleEntity)
        return saleEntity.toDto().toTransactionResponse()
    }

    // Get the sales within a date range broken down into hours
    fun getSalesByDateTimeRange(from: LocalDateTime, to: LocalDateTime? = LocalDateTime.now()): SalesResponse {
        val dto: List<SaleDto> =
            saleRepository.getSalesByDateTimeRange(from, to).map { it.toDto() }
        return generateSalesResponse(from, to, dto)
    }

    // Calculate the sale information
    private fun calculateSale(transactionRequest: TransactionRequest, transactionId: String): SaleEntity {
        return SaleEntity(
            id = UUID.randomUUID().toString(),
            transactionId = transactionId,
            finalPrice = transactionRequest.price!! * transactionRequest.priceModifier!!,
            points = Method.calculatePoint(transactionRequest.price!!, transactionRequest.paymentMethod!!),
            createdAt = transactionRequest.dateTime!!
        )
    }

    /**
     * Separate the List<SaleDto> within a date range broken down into hours return as SalesResponse
     * @param from Start date
     * @param to End date
     * @return SalesResponse
     */
    private fun generateSalesResponse(
        from: LocalDateTime, to: LocalDateTime? = LocalDateTime.now(), dto: List<SaleDto>
    ): SalesResponse {
        // Generate a list of hours within the date range and associate it with the date and time
        val generatedSalesList =
            generatedSalesList(from, to).associateBy { it.datetime.dayOfYear to it.datetime.hour }.toMutableMap()

        // Separate the list into a map of date
        val salesByDate = dto.groupBy { it.createdAt.toLocalDate() }
        // For each date, separate the list into a map of hour
        val salesByHour = salesByDate.mapValues { it.value.groupBy { it.createdAt.hour } }
        // Generate a list of hours within the date range
        val sales = mutableListOf<SalesResponse.SaleResponse>()
        // For each hour, calculate the total sales and total points
        salesByHour.forEach { (date, salesPerDay) ->
            salesPerDay.forEach { (hour, salesPerHour) ->
                val totalSales = salesPerHour.sumOf { it.finalPrice.toDouble() }
                val totalPoints = salesPerHour.sumOf { it.points.toDouble() }
                sales.add(
                    SalesResponse.SaleResponse(
                        datetime = date.atTime(hour, 0),
                        sales = totalSales.toFloat(),
                        points = totalPoints.toFloat()
                    )
                )
            }
        }
        sales.associateBy { it.datetime.dayOfYear to it.datetime.hour }.forEach { (key, value) ->
            generatedSalesList[key] = value
        }
        // Replace the first element with the “from” date
        val result = generatedSalesList.values.toMutableList()
        result[0] = SalesResponse.SaleResponse(datetime = from, sales = result[0].sales, points = result[0].points)
        return SalesResponse(result)
    }

    /**
     * Generate a list of hours within the date range
     * ex. 2021-01-01 00:19:00 to 2021-01-02 01:59:00 will return
     * 2021-01-01 00:19:00, 2021-01-01 01:00:00,...,2021-01-02 01:00:00
     * @param startTime LocalDateTime
     * @param endTime LocalDateTime
     * @return MutableList<SalesResponse.SaleResponse>
     */
    private fun generatedSalesList(
        startTime: LocalDateTime,
        endTime: LocalDateTime? = LocalDateTime.now()
    ): MutableList<SalesResponse.SaleResponse> {
        val timeList = mutableListOf<SalesResponse.SaleResponse>()
        timeList.add(SalesResponse.SaleResponse(datetime = startTime))
        var currentDateTime = startTime

        while (currentDateTime.isBefore(endTime)) {
            if (currentDateTime.minute != 0) {
                currentDateTime = currentDateTime.truncatedTo(ChronoUnit.HOURS).plusHours(1)
            }
            timeList.add(SalesResponse.SaleResponse(datetime = currentDateTime))
            currentDateTime = currentDateTime.plusHours(1)
        }

        return timeList
    }
}