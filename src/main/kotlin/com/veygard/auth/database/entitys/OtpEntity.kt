package com.veygard.auth.database.entitys

import com.veygard.auth.database.state.EntityOtpCheck
import com.veygard.auth.database.state.EntityOtpCreateResult
import com.veygard.auth.database.state.OperationDbState
import com.veygard.auth.models.auth.database.CheckOtpDbRespond
import com.veygard.auth.supports.Constants.DATA_BASE_DATE_TIME_FORMAT
import com.veygard.auth.supports.extensions.toLocalDateTime
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.varchar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object OtpEntity : Table<Nothing>("otp") {

    private val phoneNumber = varchar("phoneNumber")
    private val otpCode = varchar("otpCode")
    private val expiredAt = varchar("expiredAt")

    fun createOtp(db: Database, phone: String): OperationDbState {
        return try {
            val otp = (11111..99999).random()

            val checkPhone = db.from(OtpEntity)
                .select()
                .where {
                    phoneNumber eq phone
                }

            val list: MutableList<String> = mutableListOf()
            checkPhone.forEach { row: QueryRowSet ->
                row[phoneNumber]?.let {
                    list.add(it)
                }
            }

            val date = LocalDateTime.now().plusMinutes(3)
            val formatter = DateTimeFormatter.ofPattern(DATA_BASE_DATE_TIME_FORMAT)
            val mysqlDate = formatter.format(date)


            when {
                /*нашли номер в бд, тогда обноавляем отп код и дату*/
                list.isNotEmpty() -> {
                    db.update(OtpEntity) {
                        set(otpCode, otp.toString())
                        set(expiredAt, mysqlDate)
                        where {
                            it.phoneNumber eq list.first()
                        }
                    }
                }
                else -> {
                    db.insert(OtpEntity) {
                        set(phoneNumber, phone)
                        set(otpCode, otp.toString())
                        set(expiredAt, mysqlDate)
                    }
                }
            }
            OperationDbState.Success(result = EntityOtpCreateResult(otp.toString()))
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }

    fun checkOtp(db: Database, phone: String, otp: String): OperationDbState {
        return try {

            val result = db.from(OtpEntity)
                .select()
                .where {
                    phoneNumber eq phone
                    otpCode eq otp
                }

            var respond: CheckOtpDbRespond? = null
            result.forEach { row: QueryRowSet ->
                row[phoneNumber]?.let {
                    respond = (CheckOtpDbRespond(
                        phoneNumber = row[phoneNumber] ?: "",
                        otp = row[otpCode] ?: "",
                        expiredAt = row[expiredAt] ?: "",
                    ))
                }
            }

            return when(respond){
                null -> {
                    OperationDbState.Success(result = EntityOtpCheck(false, expired = false, message = "no match phone/otp in db"))
                }
                else ->{
                    val date = LocalDateTime.now()
                    val expired = respond?.expiredAt?.toLocalDateTime()

                    when(date.isBefore(expired)){
                        true -> {
                            OperationDbState.Success(result = EntityOtpCheck(true, expired=false, message = "Otp correct and not expired"))
                        }
                        false -> {
                            OperationDbState.Success(result = EntityOtpCheck(false, expired = true, message = "otp code was expired"))
                        }
                    }
                }
            }
        } catch (e: Exception) {
            OperationDbState.Error(message = e.message)
        }
    }
}