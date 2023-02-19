package com.any.ecommerce.posecommerce.application.controller

import com.any.ecommerce.posecommerce.application.request.SearchSalesRequest
import com.any.ecommerce.posecommerce.application.request.TransactionRequest
import com.any.ecommerce.posecommerce.application.response.SalesResponse
import com.any.ecommerce.posecommerce.application.response.TransactionResponse
import com.any.ecommerce.posecommerce.domain.service.SaleService
import com.any.ecommerce.posecommerce.domain.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Controller for transaction related endpoints
 */
@RestController
@RequestMapping("/api")
class TransactionController(
    private val saleService: SaleService,
    private val transactionService: TransactionService
) {
    /**
     * Add a transaction
     * @param transactionRequest the transaction request
     * @return the transaction response
     */
    @PostMapping("/transaction")
    fun addTransaction(@Valid @RequestBody transactionRequest: TransactionRequest): TransactionResponse {
        val transactionId = transactionService.addTransaction(transactionRequest)
        return saleService.addSale(transactionRequest, transactionId)
    }

    /**
     * Search sales by date time range
     * @param searchSalesRequest the search sales request
     * @return the sales response
     */
    @GetMapping("/sales")
    fun searchSales(@RequestBody searchSalesRequest: SearchSalesRequest): SalesResponse {
        return saleService.getSalesByDateTimeRange(searchSalesRequest.startDateTime!!, searchSalesRequest.endDateTime)
    }
}