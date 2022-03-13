package com.veygard.auth.repository.auth

import com.veygard.auth.database.state.OperationDbState
import com.veygard.auth.models.auth.User
import javax.crypto.spec.SecretKeySpec

interface AuthRepository {
    val hashKey: ByteArray
    val hmacKey:SecretKeySpec

    fun getUsers (): List<User>
    fun getUser (id:Int): OperationDbState
    fun createUser (phone:String, passwordHash:String): OperationDbState
    fun changePassword(phone:String, passwordHash:String): OperationDbState
    fun userAuthentication (phone:String, password:String): OperationDbState
    fun checkUser (phone:String): OperationDbState

    fun createOtp(phoneNum: String):OperationDbState
    fun checkOtp(phoneNum: String, otp:String):OperationDbState

    fun hashPassword(password:String):String
}