package com.veygard.auth.plugins

import com.veygard.auth.routes.authRoute
import com.veygard.auth.routes.root
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
        authRoute()

        static("assets"){
            resources("assets")
        }
    }
}
