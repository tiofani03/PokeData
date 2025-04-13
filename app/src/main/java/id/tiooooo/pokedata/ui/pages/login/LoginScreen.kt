package id.tiooooo.pokedata.ui.pages.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.component.textfield.PasswordTextField
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.pages.register.RegisterRoute
import id.tiooooo.pokedata.ui.theme.BUTTON_SIZE
import id.tiooooo.pokedata.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.pokedata.ui.theme.HUGE_PADDING
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING
import id.tiooooo.pokedata.ui.theme.SMALL_PADDING
import id.tiooooo.pokedata.utils.localization.stringRes

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    screenModel: LoginScreenModel,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    focusManager: FocusManager = LocalFocusManager.current,
) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow


    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> {
                    navigator.replaceAll(DashboardRoute())
                }

                LoginEffect.NavigateToRegister -> navigator.push(RegisterRoute())
                is LoginEffect.ShowErrorMessage -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    BaseScaffold(
        modifier = modifier,
        snackBarHostState = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = MEDIUM_PADDING)
                    .padding(top = HUGE_PADDING),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = EXTRA_LARGE_PADDING),
                    textAlign = TextAlign.Start,
                    text = stringRes("login_text_welcome_back"),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.email,
                    onValueChange = { screenModel.dispatch(LoginIntent.UpdateEmail(it)) },
                    label = { Text(stringRes("email")) },
                    singleLine = true,
                    shape = RoundedCornerShape(SMALL_PADDING)
                )

                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                PasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringRes("password"),
                    value = state.password,
                    onValueChange = { screenModel.dispatch(LoginIntent.UpdatePassword(it)) }
                )

                Spacer(modifier = Modifier.height(EXTRA_LARGE_PADDING))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BUTTON_SIZE),
                    onClick = {
                        focusManager.clearFocus(force = true)
                        screenModel.dispatch(LoginIntent.ExecuteLogin)
                    },
                    enabled = state.isButtonEnable,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {
                    Text(
                        text = if (state.isLoading) stringRes("loading") else stringRes("login"),
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(MEDIUM_PADDING))

                TextButton(
                    onClick = { screenModel.dispatch(LoginIntent.NavigateToRegister) }
                ) {
                    Text(
                        text = stringRes("login_cta_register"),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }

}