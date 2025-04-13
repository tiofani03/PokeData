package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.utils.encryptor.PasswordEncryptor
import id.tiooooo.pokedata.utils.encryptor.Sha256PasswordEncryptor
import id.tiooooo.pokedata.utils.localization.LocalizationManager
import org.koin.dsl.module

val commonModule = module {
    single<PasswordEncryptor> { Sha256PasswordEncryptor() }
    single<LocalizationManager> { LocalizationManager(get()) }
}