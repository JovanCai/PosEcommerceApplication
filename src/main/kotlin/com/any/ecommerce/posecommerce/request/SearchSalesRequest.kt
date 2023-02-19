package com.any.ecommerce.posecommerce.request

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

/**
 * Request class for search sales
 */
data class SearchSalesRequest(
    @field:NotNull
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime?
)