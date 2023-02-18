package com.any.ecommerce.posecommerce.resolver.sale

import com.any.ecommerce.posecommerce.domain.entities.SaleEntity
import com.any.ecommerce.posecommerce.service.sale.SaleService
import graphql.kickstart.tools.GraphQLMutationResolver
import lombok.AllArgsConstructor
import org.springframework.stereotype.Component

@Component
@AllArgsConstructor
class SaleMutationResolver(
    private val saleService: SaleService
) : GraphQLMutationResolver {
    fun getSalesByDateTimeRange(from: String, to: String): List<SaleEntity> {
        return saleService.getSalesByDateTimeRange(from, to)
    }
}