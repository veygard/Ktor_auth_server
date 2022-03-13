package com.veygard.auth.supports.extensions

import com.veygard.auth.supports.Constants.DATA_BASE_DATE_TIME_FORMAT
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*




fun String.toLocalDate():LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    return LocalDate.parse(this, formatter)
}

fun String.toLocalDateTime():LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(DATA_BASE_DATE_TIME_FORMAT)
    return LocalDateTime.parse(this , formatter)
}


fun String.toLocalDatTimeFromServer():LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    } catch (e:Exception){
        null
    }
}