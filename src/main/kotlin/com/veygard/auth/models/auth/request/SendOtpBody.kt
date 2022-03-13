package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpBody(
    val phoneNum: String,
)