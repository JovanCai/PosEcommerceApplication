package com.any.ecommerce.posecommerce.infrastructure

import com.any.ecommerce.posecommerce.application.response.SalesResponse
import com.any.ecommerce.posecommerce.domain.service.SaleService
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