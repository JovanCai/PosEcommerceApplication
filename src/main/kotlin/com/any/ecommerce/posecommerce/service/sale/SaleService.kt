package com.any.ecommerce.posecommerce.service.sale

import com.any.ecommerce.posecommerce.domain.entities.SaleEntity
import com.any.ecommerce.posecommerce.domain.entities.enum.Method
import com.any.ecommerce.posecommerce.dto.SaleDto
import com.any.ecommerce.posecommerce.repository.SaleRepository
import com.any.ecommerce.posecommerce.request.TransactionRequest
import com.any.ecommerce.posecommerce.response.SalesResponse
import com.any.ecommerce.posecommerce.response.TransactionResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class SaleService(
    private val saleRepository: SaleRepository
) {
    fun addSale(transactionRequest: TransactionRequest, transactionId: String): TransactionResponse {
        val saleEntity = calculateSale(transactionRequest, transactionId)
        saleRepository.save(saleEntity)
        return saleEntity.toDto().toTransactionResponse()
    }

    fun getSalesByDateTimeRange(from: LocalDateTime, to: LocalDateTime? = LocalDateTime.now()): SalesResponse {
        val dto: List<SaleDto> =
            saleRepository.getSalesByDateTimeRange(from, to).map { it.toDto() }
        return generateSalesResponse(from, to, dto)
    }

    private fun calculateSale(transactionRequest: TransactionRequest, transactionId: String): SaleEntity {
        return SaleEntity(
            id = UUID.randomUUID().toString(),
            transactionId = transactionId,
            finalPrice = transactionRequest.price * transactionRequest.priceModifier,
            points = Method.calculatePoint(transactionRequest.price, transactionRequest.paymentMethod),
            createdAt = transactionRequest.dateTime
        )
    }

    /**
     * Separate the List<SaleDto> within a date range broken down into hours return as SalesResponse
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
        val dd = generatedSalesList.values.toMutableList()
        dd[0] = SalesResponse.SaleResponse(datetime = from, sales = dd[0].sales, points = dd[0].points)
        return SalesResponse(dd)
    }

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