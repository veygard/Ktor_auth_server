package com.veygard.auth.supports

object Constants {
    const val DATABASE_HOSTNAME = "127.0.0.1"
    const val DATABASE_NAME = "cars_park"
    const val DATABASE_USERNAME = "root"
    val DATABASE_PASSWORD: String = System.getenv("KTOR_DB_PW")

    const val DATA_BASE_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"


}