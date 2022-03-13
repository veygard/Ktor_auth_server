package com.veygard.auth.models.auth

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val phoneNum: String,
    val passwordHash: String,
    val createdAt: String,
    val updatedAt: String?,
    val profileId: Int,
): Principal

fun User.toDomain()= UserClient(userId, createdAt, updatedAt)