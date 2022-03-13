package com.veygard.auth.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.root(){
    get("/") {
        call.respondText("Welcome", status = HttpStatusCode.OK)
    }
}