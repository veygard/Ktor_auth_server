package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserBody(
    val phoneNumber: String,
    val password:String
)