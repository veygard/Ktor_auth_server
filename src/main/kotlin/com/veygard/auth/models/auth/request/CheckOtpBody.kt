package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckOtpBody(
    val phoneNum: String,
    val otpCode: String,
)