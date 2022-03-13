package com.veygard.auth.models.auth

import io.ktor.auth.*
import kotlinx.serialization.Serializable


@Serializable
data class UserClient(
    val userId: Int,
    val createdAt: String,
    val updatedAt: String?,
): Principal