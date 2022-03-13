package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    val phoneNum: String,
    val password:String
)