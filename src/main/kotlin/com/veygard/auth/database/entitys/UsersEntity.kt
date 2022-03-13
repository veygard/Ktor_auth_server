package com.veygard.auth.database.entitys

import com.veygard.auth.database.state.*
import com.veygard.auth.models.auth.User
import com.veygard.auth.supports.Constants
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object UsersEntity : Table<Nothing>("users") {
    private val userId = int("userId").primaryKey()
    private val phoneNum = varchar("phoneNum")
    private val passwordHash = varchar("passwordHash")
    private val createdAt = varchar("createdAt")
    private val updatedAt = varchar("updatedAt")
    private val profileId = int("profileId")


    fun getUsersListFromQuery(db: Database): List<User> {
        val users = db.from(UsersEntity).select()

        val list: MutableList<User> = mutableListOf()
        users.forEach { row: QueryRowSet ->
            row[userId]?.let {
                list.add(
                    User(
                        userId = row[userId] ?: -1,
                        phoneNum = row[phoneNum] ?: "-1",
                        passwordHash = row[passwordHash] ?: "-1",
                        createdAt = row[createdAt] ?: "",
                        updatedAt = row[updatedAt],
                        profileId = row[profileId] ?: -1
                    )
                )
            }
        }
        return list.toList()
    }

    fun createUser(db: Database, phone: String, hash: String): OperationDbState {
        return try {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern(Constants.DATA_BASE_DATE_TIME_FORMAT)
            val mysqlDate = formatter.format(date)

            val id = db.insert(UsersEntity) {
                set(phoneNum, phone)
                set(passwordHash, hash)
                set(createdAt, mysqlDate)
                set(updatedAt, mysqlDate)
                set(profileId, null)
            }
            OperationDbState.Success(result = EntityUsersCreateResult(id.toString()))
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }

    fun changePassword(db: Database, phone: String, passwordHash: String): OperationDbState {
        return try {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern(Constants.DATA_BASE_DATE_TIME_FORMAT)
            val mysqlDate = formatter.format(date)

            db.update(UsersEntity) {
                set(UsersEntity.passwordHash, passwordHash)
                set(updatedAt, mysqlDate)
                where {
                    it.phoneNum eq phone
                }
            }
            OperationDbState.Success(result = EntityUsersChangePassResult(result = true, message = "Password was changed"))
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }

    fun getUser(db: Database, id:Int): OperationDbState {
        return try {
            val result = db.from(UsersEntity)
                .select()
                .where {
                    (userId eq id)
                }

            /*todo не знаю как из  result(Query) взять юзер-айди, приходится делать лист*/
            val list: MutableList<User> = mutableListOf()
            result.forEach { row: QueryRowSet ->
                row[userId]?.let {
                    list.add(
                        User(
                            userId = row[userId] ?: -1,
                            phoneNum = row[phoneNum] ?: "-1",
                            passwordHash = row[passwordHash] ?: "-1",
                            createdAt = row[createdAt] ?: "",
                            updatedAt = row[updatedAt],
                            profileId = row[profileId] ?: -1
                        )
                    )
                }
            }

            OperationDbState.Success(result = EntityUsersGetUserResult(user= list.first())) //если список пустой сваливаемся в кэтч

        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }

    fun checkUser(db: Database, phone: String): OperationDbState {
        return try {

            val result = db.from(UsersEntity)
                .select()
                .where {
                    phoneNum eq phone
                }

            /*todo не знаю как из  result(Query) взять юзер-айди, приходится делать лист*/
            val list: MutableList<User> = mutableListOf()
            result.forEach { row: QueryRowSet ->
                row[userId]?.let {
                    list.add(
                        User(
                            userId = row[userId] ?: -1,
                            phoneNum = row[phoneNum] ?: "-1",
                            passwordHash = row[passwordHash] ?: "-1",
                            createdAt = row[createdAt] ?: "",
                            updatedAt = row[updatedAt],
                            profileId = row[profileId] ?: -1
                        )
                    )
                }
            }

            OperationDbState.Success(result = EntityUsersCheckResult(list.isNotEmpty()))
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }

    fun userAuthentication(db: Database, phone: String, hash: String): OperationDbState {
        return try {

            val result = db.from(UsersEntity)
                .select()
                .where {
                    (phoneNum eq phone) and (passwordHash eq hash)
                }


            /*todo не знаю как из  result(Query) взять юзер-айди, приходится делать лист*/
            val list: MutableList<User> = mutableListOf()
            result.forEach { row: QueryRowSet ->
                row[userId]?.let {
                    list.add(
                        User(
                            userId = row[userId] ?: -1,
                            phoneNum = row[phoneNum] ?: "-1",
                            passwordHash = row[passwordHash] ?: "-1",
                            createdAt = row[createdAt] ?: "",
                            updatedAt = row[updatedAt],
                            profileId = row[profileId] ?: -1
                        )
                    )
                }
            }

            val userID = list.first().userId //если список пустой сваливаемся в кэтч

            OperationDbState.Success(EntityUsersLoginResult(userID.toString()))
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }
}

