package com.veygard.auth.models.auth.response

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val otpCode:String,
): Principal