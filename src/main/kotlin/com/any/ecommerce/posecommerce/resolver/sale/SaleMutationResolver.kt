package com.any.ecommerce.posecommerce.resolver.sale

import com.any.ecommerce.posecommerce.response.SalesResponse
import com.any.ecommerce.posecommerce.service.sale.SaleService
import graphql.kickstart.tools.GraphQLQueryResolver
import lombok.AllArgsConstructor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Resolver for the sale entity
 */
@Component
@AllArgsConstructor
class SaleMutationResolver(
    private val saleService: SaleService
) : GraphQLQueryResolver {
    fun getSalesByDateTimeRange(from: LocalDateTime, to: LocalDateTime): SalesResponse {
        return saleService.getSalesByDateTimeRange(from, to)
    }
}