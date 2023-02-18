package com.any.ecommerce.posecommerce.repository

import com.any.ecommerce.posecommerce.domain.entities.SaleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SaleRepository : JpaRepository<SaleEntity, String> {
    @Query(value = "SELECT * FROM sale WHERE created_at BETWEEN ?1 AND ?2", nativeQuery = true)
    public fun getSalesByDateTimeRange(from: String, to: String): List<SaleEntity>
}