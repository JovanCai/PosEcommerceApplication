package com.any.ecommerce.posecommerce.controller

import com.any.ecommerce.posecommerce.request.SearchSalesRequest
import com.any.ecommerce.posecommerce.request.TransactionRequest
import com.any.ecommerce.posecommerce.response.SalesResponse
import com.any.ecommerce.posecommerce.response.TransactionResponse
import com.any.ecommerce.posecommerce.service.sale.SaleService
import com.any.ecommerce.posecommerce.service.transaction.TransactionService
import org.springframework.web.bind.annotation.*
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
        return saleService.getSalesByDateTimeRange(searchSalesRequest.startDateTime, searchSalesRequest.endDateTime)
    }
}