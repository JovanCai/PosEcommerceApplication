package com.any.ecommerce.posecommerce.service

import com.any.ecommerce.posecommerce.application.request.TransactionRequest
import com.any.ecommerce.posecommerce.application.response.SalesResponse
import com.any.ecommerce.posecommerce.domain.service.SaleService
import com.any.ecommerce.posecommerce.infrastructure.entities.SaleEntity
import com.any.ecommerce.posecommerce.infrastructure.entities.enum.Method
import com.any.ecommerce.posecommerce.infrastructure.repository.SaleRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 * Test class for the sale service
 */
@SpringBootTest
class SaleServiceTest {
    private val saleRepository = mock(SaleRepository::class.java)
    private val saleService = SaleService(saleRepository)

    companion object {
        private const val TRANSACTION_ID_1 = "1111"
        private const val TRANSACTION_ID_2 = "2222"
        private const val TRANSACTION_ID_3 = "3333"
        private const val SALE_ID_1 = "1111"
        private const val SALE_ID_2 = "2222"
        private const val SALE_ID_3 = "3333"
    }

    // Test add sale
    @Test
    fun `test add sale`() {
        // Mock transactionRequest data
        val transactionRequest = TransactionRequest(
            price = 100.0f,
            priceModifier = 1.0f,
            paymentMethod = Method.CASH,
            dateTime = LocalDateTime.now()
        )
        assertDoesNotThrow { saleService.addSale(transactionRequest, TRANSACTION_ID_1) }
    }

    // Test get sales by date time range
    @Test
    fun `test get sales by date time range`() {
        // Mock List<SaleEntity> data
        val saleEntityList: List<SaleEntity> = listOf(
            SaleEntity(
                id = SALE_ID_1,
                transactionId = TRANSACTION_ID_1,
                finalPrice = 100.0f,
                points = 10.0f,
                createdAt = LocalDateTime.of(2021, 1, 1, 1, 1)
            ),
            SaleEntity(
                id = SALE_ID_2,
                transactionId = TRANSACTION_ID_2,
                finalPrice = 100.0f,
                points = 10.0f,
                createdAt = LocalDateTime.of(2021, 1, 1, 1, 59)
            ),
            SaleEntity(
                id = SALE_ID_3,
                transactionId = TRANSACTION_ID_3,
                finalPrice = 100.0f,
                points = 10.0f,
                createdAt = LocalDateTime.of(2021, 1, 1, 2, 1)
            )
        )

        val expectedResult = SalesResponse(
            sales = listOf(
                SalesResponse.SaleResponse(
                    sales = 200.0f,
                    points = 20.0f,
                    datetime = LocalDateTime.of(2021, 1, 1, 1, 0)
                ), SalesResponse.SaleResponse(
                    sales = 100.0f,
                    points = 10.0f,
                    datetime = LocalDateTime.of(2021, 1, 1, 2, 0)
                )
            )
        )

        `when`(
            saleRepository.getSalesByDateTimeRange(
                LocalDateTime.of(2021, 1, 1, 1, 0),
                LocalDateTime.of(2021, 1, 1, 3, 0)
            )
        ).thenReturn(saleEntityList)

        assertEquals(
            expectedResult, saleService.getSalesByDateTimeRange(
                LocalDateTime.of(2021, 1, 1, 1, 0),
                LocalDateTime.of(2021, 1, 1, 3, 0)
            )
        )
    }
}