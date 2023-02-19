package com.any.ecommerce.posecommerce.exception

import com.any.ecommerce.posecommerce.infrastructure.entities.enum.Method
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorResponse(val error: String)

/**
 * Global exception handler
 */
@ControllerAdvice
class GlobalExceptionHandler {
    // Handle HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(getErrorMessage(e).substringAfter(", problem: "))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.message ?: "Unknown error")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    private fun getErrorMessage(ex: HttpMessageNotReadableException): String {
        val message = ex.message ?: "Unknown error"
        // Check if JSON don't have unexpected character
        if (message.contains("Unexpected character")) {
            return "JSON must not have unexpected character"
        }
        // Check if the error is due to invalid enum value
        if (message.contains("com.any.ecommerce.posecommerce.domain.entities.enum.Method")) {
            return "payment method must be included in ${Method.values().joinToString(", ")}"
        }
        // Check if the error is due to invalid date time
        if (message.contains("java.time.LocalDateTime")) {
            return "date time must be properly formatted as yyyy-MM-ddTHH:mm:ssZ"
        }
        return message
    }
}