package com.any.ecommerce.posecommerce.repository

import com.any.ecommerce.posecommerce.domain.entities.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository : JpaRepository<TransactionEntity, String> {
}