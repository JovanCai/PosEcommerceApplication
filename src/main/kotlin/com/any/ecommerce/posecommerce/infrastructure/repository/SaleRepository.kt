package com.any.ecommerce.posecommerce.infrastructure.repository

import com.any.ecommerce.posecommerce.infrastructure.entities.SaleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

/**
 * Repository for the sale entity
 */
interface SaleRepository : JpaRepository<SaleEntity, String> {
    // Get sales data between the given dates
    @Query(value = "SELECT * FROM sale WHERE created_at BETWEEN ?1 AND ?2", nativeQuery = true)
    fun getSalesByDateTimeRange(from: LocalDateTime, to: LocalDateTime? = LocalDateTime.now()): List<SaleEntity>
}