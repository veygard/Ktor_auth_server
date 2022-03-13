package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordBody(
    val phoneNumber: String,
    val password:String
)