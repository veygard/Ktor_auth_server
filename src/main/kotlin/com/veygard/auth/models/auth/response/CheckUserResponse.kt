package com.veygard.auth.models.auth.response

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class CheckUserResponse(
    val isFound: Boolean,
    val msg: String? = null
): Principal