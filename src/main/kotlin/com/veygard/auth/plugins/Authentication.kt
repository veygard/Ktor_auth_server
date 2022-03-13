package com.veygard.auth.plugins

import com.veygard.auth.auth.JwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

val jwtConfig = JwtConfig(System.getenv("KTOR_CARS_PARK_JWT_SECRET"))

fun Application.configureAuthentication(){
    install(Authentication){
        jwt("auth"){
            jwtConfig.configureKtorFeature(this)
        }
    }
}