package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.ui.pages.login.LoginScreenModel
import id.tiooooo.pokedata.ui.pages.register.RegisterScreenModel
import id.tiooooo.pokedata.ui.pages.splash.SplashScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    single { SplashScreenModel(get(), get()) }
    single { LoginScreenModel(get()) }
    single { RegisterScreenModel(get()) }
}