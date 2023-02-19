package com.any.ecommerce.posecommerce.application.request

import java.time.LocalDateTime

/**
 * Request class for search sales
 */
data class SearchSalesRequest(
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?
) {
    init {
        // Verify start date time is included
        if (startDateTime == null) throw IllegalArgumentException("start date time must be included")

        // Verify end date time is after start date time
        if (endDateTime!!.isBefore(startDateTime)) throw IllegalArgumentException("end date time must be after start date time")
    }
}