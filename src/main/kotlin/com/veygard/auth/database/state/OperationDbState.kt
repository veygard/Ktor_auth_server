package com.veygard.auth.database.state

import com.veygard.auth.models.auth.User

sealed class OperationDbState {
    data class Success(val result:EntityResultState? = null): OperationDbState()
    data class Error(val message:String? = null): OperationDbState()
}

interface EntityResultState

data class EntityUsersCheckResult(val isFound:Boolean):EntityResultState
data class EntityUsersGetUserResult(val user:User):EntityResultState
data class EntityUsersLoginResult(val id:String):EntityResultState
data class EntityUsersCreateResult(val id:String):EntityResultState
data class EntityUsersChangePassResult(val result: Boolean, val message:String):EntityResultState
data class EntityOtpCreateResult(val otp: String):EntityResultState
data class EntityOtpCheck(val result: Boolean, val expired:Boolean, val message: String? = null):EntityResultState