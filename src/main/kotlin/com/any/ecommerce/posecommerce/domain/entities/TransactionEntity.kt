package com.any.ecommerce.posecommerce.domain.entities

import com.any.ecommerce.posecommerce.domain.entities.enum.Method
import com.any.ecommerce.posecommerce.request.TransactionRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

/**
 * Entity class for transaction
 */
@Entity
@Table(name = "transaction")
data class TransactionEntity(
    @Id
    @Column(name = "id")
    val id: String,

    @Column(name = "price")
    val price: Float,

    @Column(name = "price_modifier")
    val priceModifier: Float,

    @Column(name = "method")
    val method: Method,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromRequest(request: TransactionRequest): TransactionEntity {
            return TransactionEntity(
                id = UUID.randomUUID().toString(),
                price = request.price,
                priceModifier = request.priceModifier,
                method = request.paymentMethod,
                createdAt = request.dateTime
            )

        }
    }
}