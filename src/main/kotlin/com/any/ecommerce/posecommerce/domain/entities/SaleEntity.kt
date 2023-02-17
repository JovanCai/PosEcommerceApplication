package com.any.ecommerce.posecommerce.domain.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
@Getter
@Setter
public class SaleEntity {
    @Id
    @Column(name = "id")
    private val id: String = ""
    @Column(name = "transaction_id")
    private val transactionId:String = ""
    @Column(name = "final_price")
    private val finalPrice:Float = 0.0f
    @Column(name = "points")
    private val points:Float = 0.0f
    @Column(name = "created_at")
    private val createdAt:LocalDateTime = LocalDateTime.now()

}