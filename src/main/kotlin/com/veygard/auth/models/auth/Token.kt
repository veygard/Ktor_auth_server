package com.veygard.auth.models.auth

import io.ktor.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class Token(val jwt:String): Principal