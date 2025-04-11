package id.tiooooo.pokedata.ui.pages.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity
import id.tiooooo.pokedata.data.implementation.repository.UserTempRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenModel(
    private val userTempRepository: UserTempRepository,
) : ScreenModel {

    private val _userList = MutableStateFlow<List<UserEntity>>(emptyList())
    val userList = _userList.asStateFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    fun executeUsers() {
        screenModelScope.launch {
//            _userList.value = data
        }
    }

    fun executeLogin(email: String, password: String) {
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isLoginSuccess = false) }

            val user = userTempRepository.login(email, password)

            if (user != null) {
                _state.update { it.copy(isLoading = false, isLoginSuccess = true) }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Email atau password salah",
                    )
                }
            }
        }
    }

}

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccess: Boolean = false
)