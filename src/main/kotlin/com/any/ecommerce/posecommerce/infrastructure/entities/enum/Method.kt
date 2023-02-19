package com.any.ecommerce.posecommerce.infrastructure.entities.enum

/**
 * Enum class for payment method
 */
enum class Method(val priceModifierFrom: Float, val priceModifierTo: Float) {
    CASH(0.9f, 1f),
    CASH_ON_DELIVERY(1f, 1.02f),
    VISA(0.95f, 1f),
    MASTERCARD(0.95f, 1f),
    AMEX(0.98f, 1.01f),
    JCB(0.95f, 1f);

    companion object {
        fun calculatePoint(price: Float, method: Method): Float {
            return when (method) {
                CASH -> price * 0.05f
                CASH_ON_DELIVERY -> price * 0.05f
                VISA -> price * 0.03f
                MASTERCARD -> price * 0.03f
                AMEX -> price * 0.02f
                JCB -> price * 0.05f
            }
        }
    }
}