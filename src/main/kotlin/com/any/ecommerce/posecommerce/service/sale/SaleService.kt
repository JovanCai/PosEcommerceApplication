package com.any.ecommerce.posecommerce.service.sale

import com.any.ecommerce.posecommerce.domain.entities.SaleEntity
import com.any.ecommerce.posecommerce.domain.entities.enum.Method
import com.any.ecommerce.posecommerce.repository.SaleRepository
import com.any.ecommerce.posecommerce.request.TransactionRequest
import com.any.ecommerce.posecommerce.response.TransactionResponse
import org.springframework.stereotype.Service
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

    fun getSalesByDateTimeRange(from: String, to: String) = saleRepository.getSalesByDateTimeRange(from, to)

    private fun calculateSale(transactionRequest: TransactionRequest, transactionId: String): SaleEntity {
        return SaleEntity(
            id = UUID.randomUUID().toString(),
            transactionId = transactionId,
            finalPrice = transactionRequest.price * transactionRequest.priceModifier,
            points = Method.calculatePoint(transactionRequest.price, transactionRequest.paymentMethod),
            createdAt = transactionRequest.dateTime
        )
    }
}