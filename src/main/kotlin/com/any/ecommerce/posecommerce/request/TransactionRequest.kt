package com.any.ecommerce.posecommerce.request

import com.any.ecommerce.posecommerce.domain.entities.enum.Method
import java.time.LocalDateTime

/**
 * Request class for transaction
 */
data class TransactionRequest(
    var price: Float?,

    var priceModifier: Float?,

    var paymentMethod: Method?,

    var dateTime: LocalDateTime?,
) {
    init {
        // Verify price is a number
        if (price == null || price!!.isNaN()) throw IllegalArgumentException("price must be a number")

        // Verify payment method is included
        if (paymentMethod == null) throw IllegalArgumentException("payment method must be included")

        // Verify price modifier is a number
        if (priceModifier == null || priceModifier!!.isNaN()) throw IllegalArgumentException("price modifier must be a number")
        // Verify price modifier is between price modifier from and price modifier to
        if (paymentMethod!!.priceModifierFrom > priceModifier!! || paymentMethod!!.priceModifierTo < priceModifier!!) throw IllegalArgumentException(
            "price modifier must be between ${paymentMethod!!.priceModifierFrom} and ${paymentMethod!!.priceModifierTo}"
        )

        // Verify date time is included
        if (dateTime == null) throw IllegalArgumentException("date time must be included")
        // Verify date time is not in the future
        if (dateTime!!.isAfter(LocalDateTime.now())) throw IllegalArgumentException("date time must not be in the future")
    }
}