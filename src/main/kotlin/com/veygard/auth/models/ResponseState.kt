package com.veygard.auth.models

import kotlinx.serialization.Serializable

@Serializable
sealed class ResponseState(open val message: String = "")  {
    object Success: ResponseState()
    data class Error(override val message:String = ""): ResponseState()
}