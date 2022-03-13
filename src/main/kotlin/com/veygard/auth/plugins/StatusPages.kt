package com.veygard.auth.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages(){
    install(StatusPages){
        status(HttpStatusCode.NotFound){
            call.respond(
                message = "Page not found",
                status = HttpStatusCode.NotFound
            )
        }
        exception<Exception>{
            call.respond(
                message = "Server was crushed",
                status = HttpStatusCode.ExpectationFailed
            )
        }
    }
}