package com.any.ecommerce.posecommerce.domain.entities.enum

enum class Method {
    CASH,
    CASH_ON_DELIVERY,
    VISA,
    MASTERCARD,
    AMEX,
    JCB;

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