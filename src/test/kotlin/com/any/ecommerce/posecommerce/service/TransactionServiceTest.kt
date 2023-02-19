package com.any.ecommerce.posecommerce.service

import com.any.ecommerce.posecommerce.application.request.TransactionRequest
import com.any.ecommerce.posecommerce.domain.service.TransactionService
import com.any.ecommerce.posecommerce.infrastructure.entities.enum.Method
import com.any.ecommerce.posecommerce.infrastructure.repository.TransactionRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 * Test class for the transaction service
 */
@SpringBootTest
class TransactionServiceTest {
    private val transactionRepository = mock(TransactionRepository::class.java)
    private val transactionService = TransactionService(transactionRepository)

    // Test add transaction
    @Test
    fun `test add transaction`() {
        // Mock transactionRequest data
        val transactionRequest = TransactionRequest(
            price = 100.0f,
            priceModifier = 1.0f,
            paymentMethod = Method.CASH,
            dateTime = LocalDateTime.now()
        )
        assertDoesNotThrow { transactionService.addTransaction(transactionRequest) }
    }
}