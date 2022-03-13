package com.veygard.auth.models.auth.response

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class ChangePassResponse(
    val isSuccess: Boolean,
    val msg: String? = null
): Principal