package com.any.ecommerce.posecommerce.repository

import com.any.ecommerce.posecommerce.domain.entities.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Transaction repository
 */
interface TransactionRepository : JpaRepository<TransactionEntity, String> {
}