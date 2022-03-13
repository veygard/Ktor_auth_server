package com.veygard.auth.models.auth.response

import com.veygard.auth.database.state.EntityOtpCheck
import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class CheckOtpResponse(
    val result: Boolean, val expired:Boolean? = null, val message: String? = null
): Principal

fun EntityOtpCheck.toResponse():CheckOtpResponse = CheckOtpResponse(
    result, expired, message
)