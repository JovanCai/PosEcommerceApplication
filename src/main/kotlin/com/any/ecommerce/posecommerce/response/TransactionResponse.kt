package com.any.ecommerce.posecommerce.response

/**
 * Response for the transaction query
 */
data class TransactionResponse(
    var finalPrice: Float,
    var points: Float
)