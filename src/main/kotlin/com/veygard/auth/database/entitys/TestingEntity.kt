package com.veygard.auth.database.entitys

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TestingEntity: Table<Nothing>("testing_table") {
    val id =int("id")
    val name = varchar("name")
    val explanation = varchar("explanation")
    val date = varchar("date")
}