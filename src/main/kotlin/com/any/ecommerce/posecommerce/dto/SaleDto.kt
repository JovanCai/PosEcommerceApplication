package com.any.ecommerce.posecommerce.dto

import com.any.ecommerce.posecommerce.response.TransactionResponse
import java.time.LocalDateTime

data class SaleDto(
    val finalPrice: Float,
    val points: Float,
    val createdAt: LocalDateTime
) {
    fun toTransactionResponse() = TransactionResponse(
        finalPrice = finalPrice,
        points = points
    )
}