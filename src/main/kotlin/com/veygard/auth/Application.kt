package com.veygard.auth

import com.veygard.auth.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin() //Koin always needs first
    configureSerialization()
    configureAuthentication()
    configureMonitoring()
    configureDefaultHeader()
    configureStatusPages()
    configureRouting()
}
