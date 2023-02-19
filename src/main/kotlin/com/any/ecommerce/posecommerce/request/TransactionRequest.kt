package com.any.ecommerce.posecommerce.request

import com.any.ecommerce.posecommerce.domain.entities.enum.Method
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

/**
 * Request class for transaction
 */
data class TransactionRequest(
    @field:NotNull
    var price: Float,

    @field:NotNull
    var priceModifier: Float,

    @field:NotNull
    var paymentMethod: Method,

    @field:NotNull
    var dateTime: LocalDateTime,
)