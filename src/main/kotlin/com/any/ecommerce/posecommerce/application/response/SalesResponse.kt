package com.any.ecommerce.posecommerce.application.response

import java.time.LocalDateTime

/**
 * Response for the sales query
 */
data class SalesResponse(
    val sales: List<SaleResponse>
) {
    data class SaleResponse(
        val sales: Float? = 0.00f,
        val points: Float? = 0.00f,
        val datetime: LocalDateTime
    )
}