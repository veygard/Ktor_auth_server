package com.veygard.auth.database

import com.veygard.auth.database.state.OperationDbState
import com.veygard.auth.models.auth.User

interface DatabaseManager {
    fun getAllUsers(): List<User>
    fun createUser(phoneNum:String, passwordHash:String): OperationDbState
    fun changePassword(phoneNum:String, passwordHash:String): OperationDbState
    fun checkUser(phoneNum:String): OperationDbState
    fun getUser(userId:Int): OperationDbState
    fun userAuthentication(phoneNum:String, passwordHash:String): OperationDbState

    fun createOtp(phoneNum: String):OperationDbState
    fun checkOtp(phoneNum: String, otp:String):OperationDbState
}