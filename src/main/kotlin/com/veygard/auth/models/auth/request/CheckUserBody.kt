package com.veygard.auth.models.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class CheckUserBody(
    val phoneNum: String,
)