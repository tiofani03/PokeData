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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.component.textfield.GeneralTextField
import id.tiooooo.pokedata.ui.component.textfield.PasswordTextField
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    screenModel: RegisterScreenModel,
) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                is RegisterEffect.NavigateToLogin -> navigator.replaceAll(DashboardRoute())
                is RegisterEffect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    BaseScaffold(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            GeneralTextField(
                value = state.email,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateEmail(it)) },
                label = "Email",
                errorMessage = state.emailError
            )

            GeneralTextField(
                value = state.name,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateName(it)) },
                label = "Nama",
                errorMessage = state.nameError
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordTextField(
                label = "Password",
                value = state.password,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdatePassword(it)) },
                errorMessage = state.passwordError,
            )

            PasswordTextField(
                label = "Confirm Password",
                value = state.confirmPassword,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateConfirmPassword(it)) },
                errorMessage = state.confirmPasswordError,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { screenModel.dispatch(RegisterIntent.ExecuteRegister) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Daftar")
            }

            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = state.errorMessage!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
