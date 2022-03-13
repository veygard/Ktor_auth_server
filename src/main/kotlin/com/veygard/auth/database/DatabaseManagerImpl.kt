package com.veygard.auth.database

import com.veygard.auth.database.entitys.OtpEntity
import com.veygard.auth.database.entitys.UsersEntity
import com.veygard.auth.database.entitys.UsersEntity.getUsersListFromQuery
import com.veygard.auth.database.state.OperationDbState
import com.veygard.auth.models.auth.User
import com.veygard.auth.supports.Constants.DATABASE_HOSTNAME
import com.veygard.auth.supports.Constants.DATABASE_NAME
import com.veygard.auth.supports.Constants.DATABASE_PASSWORD
import com.veygard.auth.supports.Constants.DATABASE_USERNAME
import org.ktorm.database.Database

class DatabaseManagerImpl : DatabaseManager {

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl =
            "jdbc:mysql://$DATABASE_HOSTNAME:3306/$DATABASE_NAME?user=$DATABASE_USERNAME&password=$DATABASE_PASSWORD&useSSL=false&serverTimezone=UTC"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    override fun getAllUsers(): List<User> {
        return getUsersListFromQuery(ktormDatabase)
    }

    override fun createUser(phoneNum: String, passwordHash: String): OperationDbState {
        return UsersEntity.createUser(ktormDatabase, phoneNum, passwordHash)
    }

    override fun changePassword(phoneNum: String, passwordHash: String): OperationDbState {
        return UsersEntity.changePassword(ktormDatabase, phoneNum, passwordHash)
    }

    override fun checkUser(phoneNum: String): OperationDbState {
        return UsersEntity.checkUser(ktormDatabase, phoneNum)
    }

    override fun getUser(userId: Int): OperationDbState {
        return UsersEntity.getUser(ktormDatabase, userId)
    }

    override fun userAuthentication(phoneNum: String, passwordHash: String): OperationDbState {
        return UsersEntity.userAuthentication(ktormDatabase, phoneNum, passwordHash)
    }

    override fun createOtp(phoneNum: String): OperationDbState {
        return OtpEntity.createOtp(ktormDatabase, phoneNum)
    }

    override fun checkOtp(phoneNum: String, otp: String): OperationDbState {
        return OtpEntity.checkOtp(ktormDatabase, phoneNum, otp)
    }


}