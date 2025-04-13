package id.tiooooo.pokedata.ui.pages.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.component.textfield.GeneralTextField
import id.tiooooo.pokedata.ui.component.textfield.PasswordTextField
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.pages.detail.component.DetailAppBar
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.utils.localization.stringRes

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    screenModel: RegisterScreenModel,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    focusManager: FocusManager = LocalFocusManager.current,
) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is RegisterEffect.NavigateToLogin -> navigator.replaceAll(DashboardRoute())
                is RegisterEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    BaseScaffold(
        modifier = modifier,
        topBar = {
            DetailAppBar(title = "") {
                navigator.pop()
            }
        },
        snackBarHostState = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.padding(bottom = EXTRA_LARGE_PADDING),
                text = stringRes("register_title"),
                style = MaterialTheme.typography.headlineSmall
            )

            GeneralTextField(
                value = state.email,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateEmail(it)) },
                label = stringRes("email"),
                errorMessage = state.emailError
            )

            GeneralTextField(
                value = state.name,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateName(it)) },
                label = stringRes("register_name"),
                errorMessage = state.nameError
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            PasswordTextField(
                label = stringRes("register_password"),
                value = state.password,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdatePassword(it)) },
                errorMessage = state.passwordError,
            )

            PasswordTextField(
                label = stringRes("register_confirmation_password"),
                value = state.confirmPassword,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateConfirmPassword(it)) },
                errorMessage = state.confirmPasswordError,
            )

            Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    focusManager.clearFocus(force = true)
                    screenModel.dispatch(RegisterIntent.ExecuteRegister)
                },
                enabled = !state.isLoading
            ) {
                Text(stringRes("register"))
            }
        }
    }
}
