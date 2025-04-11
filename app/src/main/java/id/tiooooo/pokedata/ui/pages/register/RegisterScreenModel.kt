package id.tiooooo.pokedata.ui.pages.register

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import id.tiooooo.pokedata.data.implementation.local.entity.UserEntity
import id.tiooooo.pokedata.data.implementation.repository.UserTempRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterScreenModel(
    private val userTempRepository: UserTempRepository
) : ScreenModel {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onPasswordChanged(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _state.update { it.copy(confirmPassword = confirmPassword) }
    }

    private val _effect = MutableSharedFlow<RegisterEffect>()
    val effect: SharedFlow<RegisterEffect> = _effect


    fun validatePassword() {
        val current = state.value
        if (current.password != current.confirmPassword) {
            _state.update {
                it.copy(
                    errorMessage = "Password tidak cocok",
                    isButtonEnable = false
                )
            }
            return
        }
        _state.update {
            it.copy(isButtonEnable = true)
        }
    }

    fun register() {
        val current = state.value
        screenModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null, isButtonEnable = false) }

            val isEmailUsed = userTempRepository.getUserByEmail(current.email)
            if (isEmailUsed != null) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isButtonEnable = true,
                        errorMessage = "Email sudah digunakan"
                    )
                }
                return@launch
            }


            try {
                val userEntity = UserEntity(
                    uuid = UUID.randomUUID().toString(),
                    email = current.email,
                    password = current.password,
                    username = "Pikachu"
                )
                userTempRepository.register(userEntity)
                _state.update { it.copy(isLoading = false, isButtonEnable = true) }
                _effect.emit(RegisterEffect.NavigateToLogin)

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Terjadi kesalahan saat registrasi"
                    )
                }
            }
        }
    }
}

sealed interface RegisterEffect {
    data object NavigateToLogin : RegisterEffect
    data class ShowError(val message: String) : RegisterEffect
}


data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isButtonEnable: Boolean = false,
    val errorMessage: String? = null
)
