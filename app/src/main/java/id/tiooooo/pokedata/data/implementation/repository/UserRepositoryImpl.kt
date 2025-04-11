package id.tiooooo.pokedata.data.implementation.repository

import id.tiooooo.pokedata.data.api.repository.UserRepository
import id.tiooooo.pokedata.data.implementation.local.datastore.AppDatastore
import kotlinx.coroutines.flow.first

class UserRepositoryImpl(
    private val appDatastore: AppDatastore,
) : UserRepository {

    override suspend fun isLogin(): Boolean {
        return appDatastore.isLoggedInFlow.first()
    }
}