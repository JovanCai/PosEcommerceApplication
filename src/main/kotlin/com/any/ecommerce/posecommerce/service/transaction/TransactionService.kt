package com.any.ecommerce.posecommerce.service.transaction

import com.any.ecommerce.posecommerce.domain.entities.TransactionEntity
import com.any.ecommerce.posecommerce.repository.TransactionRepository
import com.any.ecommerce.posecommerce.request.TransactionRequest
import org.springframework.stereotype.Service

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    fun addTransaction(transactionRequest: TransactionRequest): String {
        val entity = TransactionEntity.fromRequest(transactionRequest)
        transactionRepository.save(entity)
        return entity.id
    }
}