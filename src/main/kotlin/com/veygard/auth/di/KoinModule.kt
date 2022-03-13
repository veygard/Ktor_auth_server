package com.veygard.auth.di

import com.veygard.auth.database.DatabaseManager
import com.veygard.auth.database.DatabaseManagerImpl
import com.veygard.auth.repository.auth.AuthRepository
import com.veygard.auth.repository.auth.AuthRepositoryImpl
import org.koin.dsl.module


val authKoinModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
}

val sqlModule = module {
    single<DatabaseManager> { DatabaseManagerImpl() }
}
