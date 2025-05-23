package id.tiooooo.pokedata.di

import id.tiooooo.pokedata.ui.pages.dashboard.DashboardScreenModel
import id.tiooooo.pokedata.ui.pages.detail.DetailScreenModel
import id.tiooooo.pokedata.ui.pages.home.HomeScreenModel
import id.tiooooo.pokedata.ui.pages.login.LoginScreenModel
import id.tiooooo.pokedata.ui.pages.profile.ProfileScreenModel
import id.tiooooo.pokedata.ui.pages.register.RegisterScreenModel
import id.tiooooo.pokedata.ui.pages.splash.SplashScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    factory { SplashScreenModel(get(), get(), get(), get()) }
    factory { LoginScreenModel(get(), get()) }
    factory { RegisterScreenModel(get(), get()) }
    factory { DashboardScreenModel() }
    factory { ProfileScreenModel(get(), get(), get()) }
    factory { HomeScreenModel(get()) }
    factory { DetailScreenModel(get()) }
}