package com.any.ecommerce.posecommerce.request

import java.time.LocalDateTime
import javax.validation.constraints.NotNull

data class SearchSalesRequest(
    @field:NotNull
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime?
)