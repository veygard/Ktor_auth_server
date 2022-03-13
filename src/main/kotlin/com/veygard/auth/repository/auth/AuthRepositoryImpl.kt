package com.veygard.auth.repository.auth

import com.veygard.auth.database.DatabaseManagerImpl
import com.veygard.auth.database.state.OperationDbState
import com.veygard.auth.models.auth.User
import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class AuthRepositoryImpl : AuthRepository {

    override val hashKey: ByteArray
        get() = System.getenv("HASH_SECRET_KEY").toByteArray()
    override val hmacKey: SecretKeySpec
        get() = SecretKeySpec(hashKey, "HmacSHA1")

    private val databaseManager = DatabaseManagerImpl()


    override fun getUsers(): List<User> {
        return databaseManager.getAllUsers()
    }

    override fun getUser(id: Int): OperationDbState {
        return databaseManager.getUser(id)
    }

    override fun createUser(phone: String, passwordHash: String): OperationDbState {
        return databaseManager.createUser(phone, passwordHash)
    }

    override fun changePassword(phone: String, passwordHash: String): OperationDbState {
        return databaseManager.changePassword(phone, passwordHash)
    }

    override fun userAuthentication(phone: String, password: String): OperationDbState {
        return databaseManager.userAuthentication(phone, password)
    }

    override fun checkUser(phone: String): OperationDbState {
        return databaseManager.checkUser(phone)
    }

    override fun createOtp(phoneNum: String): OperationDbState {
        return databaseManager.createOtp(phoneNum)
    }

    override fun checkOtp(phoneNum: String, otp: String): OperationDbState {
        return databaseManager.checkOtp(phoneNum, otp)
    }

    override fun hashPassword(password: String): String {
        val hmac = Mac.getInstance("HmacSHA1")
        hmac.init(hmacKey)
        return hex((hmac.doFinal(password.toByteArray(Charsets.UTF_8))))
    }


}