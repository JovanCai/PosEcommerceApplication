package com.any.ecommerce.posecommerce.domain.entities

import com.any.ecommerce.posecommerce.dto.SaleDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

/**
 * Entity class for sale
 */
@Entity
@Table(name = "sale")
data class SaleEntity(
    @Id
    @Column(name = "id")
    val id: String = UUID.randomUUID().toString(),

    @Column(name = "transaction_id")
    val transactionId: String,

    @Column(name = "final_price")
    val finalPrice: Float,

    @Column(name = "points")
    val points: Float,

    @Column(name = "created_at")
    val createdAt: LocalDateTime
) {
    fun toDto() = SaleDto(
        finalPrice = finalPrice,
        points = points,
        createdAt = createdAt
    )
}