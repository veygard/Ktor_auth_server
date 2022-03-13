package com.veygard.auth.routes

import com.veygard.auth.auth.JwtConfig
import com.veygard.auth.database.state.*
import com.veygard.auth.models.auth.Token
import com.veygard.auth.models.auth.request.*
import com.veygard.auth.models.auth.response.*
import com.veygard.auth.models.auth.toDomain
import com.veygard.auth.plugins.jwtConfig
import com.veygard.auth.repository.auth.AuthRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoute() {
    val authRepository: AuthRepository by inject()


    post("/send_otp") {
        val sendOtpBody: SendOtpBody?
        try {
            sendOtpBody = call.receive()
        } catch (e: Exception) {
            call.respondText { "Invalid body params" }
            return@post
        }

        when (val request = authRepository.createOtp(sendOtpBody.phoneNum)) {
            is OperationDbState.Success -> {
                val otp = when (request.result) {
                    is EntityOtpCreateResult -> {
                        request.result.otp
                    }
                    else -> null
                }

                otp?.let {
                    call.respond(HttpStatusCode.OK, SendOtpResponse(it))
                }
                    ?: run {
                        call.respond(HttpStatusCode.ExpectationFailed, "cant send Otp")
                    }
            }
            is OperationDbState.Error -> {
                call.respond(HttpStatusCode.ExpectationFailed, "cant send Otp")
            }
        }
    }

    post("/check_otp") {
        val checkOtpBody: CheckOtpBody?
        try {
            checkOtpBody = call.receive()
        } catch (e: Exception) {
            call.respondText { "Invalid body params" }
            return@post
        }

        when (val request = authRepository.checkOtp(checkOtpBody.phoneNum, checkOtpBody.otpCode)) {
            is OperationDbState.Success -> {
                when (request.result) {
                    is EntityOtpCheck -> {
                        call.respond(HttpStatusCode.OK, request.result.toResponse())
                    }
                    else -> {
                        call.respond(HttpStatusCode.ExpectationFailed, "cant check Otp")
                    }
                }
            }
            is OperationDbState.Error -> {
                call.respond(HttpStatusCode.ExpectationFailed, "cant check Otp")
            }
        }
    }

    post("/login") {
        val loginBody: LoginBody?
        try {
            loginBody = call.receive()
        } catch (e: Exception) {
            call.respondText { "Invalid body params" }
            return@post
        }

        val hashPassword = authRepository.hashPassword(loginBody.password)

        when (val request = authRepository.userAuthentication(loginBody.phoneNum, hashPassword)) {
            is OperationDbState.Success -> {
                val token = Token(
                    jwtConfig.generateToken(
                        JwtConfig.JwtUser(
                            userId = when (request.result) {
                                is EntityUsersLoginResult -> request.result.id
                                else -> "-1"
                            }
                        )
                    )
                )
                call.respond(HttpStatusCode.OK, token)
            }

            is OperationDbState.Error -> {
                call.respond(HttpStatusCode.Unauthorized, "Invalid user/password{${request.message}}")
            }
        }
    }

//вывод всех юзеров
    get("/users") {
        val users = authRepository.getUsers()
        call.respond(users)
    }

    authenticate("auth") {
        get("/get-user/{id}") {
            val userID = call.parameters["id"]
            when (val request = authRepository.getUser(userID?.toInt() ?: -1)) {
                is OperationDbState.Success -> {
                    val user = when (request.result) {
                        is EntityUsersGetUserResult -> request.result.user
                        else -> null
                    }
                    user?.let {
                        call.respond(user.toDomain())
                    }
                }
                is OperationDbState.Error -> {
                    call.respond(message = "something wrong ${request.message}", status = HttpStatusCode.NotFound)
                }
            }
        }
    }

    post("/check-user") {
        val checkUserBody: CheckUserBody?
        try {
            checkUserBody = call.receive()
        } catch (e: Exception) {
            call.respondText { "Invalid body params" }
            return@post
        }

        when (val request = authRepository.checkUser(checkUserBody.phoneNum)) {
            is OperationDbState.Success -> {
                call.respond(
                    HttpStatusCode.OK, CheckUserResponse(
                        when (request.result) {
                            is EntityUsersCheckResult -> request.result.isFound
                            else -> false
                        }
                    )
                )
            }
            is OperationDbState.Error -> {
                call.respond(HttpStatusCode.Unauthorized, CheckUserResponse(false, msg = "OperationDbState.Error"))
            }
        }

    }
        post("/change-password") {
            val body: ChangePasswordBody?
            try {
                body = call.receive()
            } catch (e: Exception) {
                call.respondText { "Invalid body params" }
                return@post
            }

            val hashPassword = authRepository.hashPassword(body.password)

            when (val result = authRepository.changePassword(body.phoneNumber, hashPassword)) {
                is OperationDbState.Success -> {
                    call.respond(HttpStatusCode.OK, ChangePassResponse(isSuccess = true, msg = "Password was changed"))
                }
                is OperationDbState.Error -> {
                    call.respond(HttpStatusCode.BadRequest, message = "something wrong ${result.message}")
                }
            }
        }

    post("/createUser") {
        val body: CreateUserBody?
        try {
            body = call.receive()
        } catch (e: Exception) {
            call.respondText { "Invalid body params" }
            return@post
        }

        val hashPassword = authRepository.hashPassword(body.password)

        when (val result = authRepository.createUser(body.phoneNumber, hashPassword)) {
            is OperationDbState.Success -> {
                call.respond(HttpStatusCode.OK, CreateUserResponse(isSuccess = true, msg = "User was created"))
            }
            is OperationDbState.Error -> {
                call.respond(HttpStatusCode.BadRequest, message = "something wrong ${result.message}")
            }
        }
    }

}