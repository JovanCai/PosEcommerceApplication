package com.any.ecommerce.posecommerce.infrastructure.repository

import com.any.ecommerce.posecommerce.infrastructure.entities.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Transaction repository
 */
interface TransactionRepository : JpaRepository<TransactionEntity, String> {
}