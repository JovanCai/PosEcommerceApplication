package com.any.ecommerce.posecommerce.domain.service

import com.any.ecommerce.posecommerce.application.request.TransactionRequest
import com.any.ecommerce.posecommerce.infrastructure.entities.TransactionEntity
import com.any.ecommerce.posecommerce.infrastructure.repository.TransactionRepository
import org.springframework.stereotype.Service

/**
 * Service class for transaction
 */
@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {
    // Add a transaction
    fun addTransaction(transactionRequest: TransactionRequest): String {
        val entity = TransactionEntity.fromRequest(transactionRequest)
        transactionRepository.save(entity)
        return entity.id
    }
}