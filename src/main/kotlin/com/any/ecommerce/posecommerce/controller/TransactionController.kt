package com.any.ecommerce.posecommerce.controller

import com.any.ecommerce.posecommerce.request.TransactionRequest
import com.any.ecommerce.posecommerce.response.TransactionResponse
import com.any.ecommerce.posecommerce.service.sale.SaleService
import com.any.ecommerce.posecommerce.service.transaction.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TransactionController(
    private val saleService: SaleService,
    private val transactionService: TransactionService
) {
    @PostMapping("/transaction")
    fun addTransaction(@Valid @RequestBody transactionRequest: TransactionRequest): TransactionResponse {
        val transactionId = transactionService.addTransaction(transactionRequest)
        return saleService.addSale(transactionRequest, transactionId)
    }

    @GetMapping("/sales")
    fun searchSales() = saleService.getSalesByDateTimeRange("2022-09-01T00:00:00Z", "2022-09-01T23:59:59Z")
}