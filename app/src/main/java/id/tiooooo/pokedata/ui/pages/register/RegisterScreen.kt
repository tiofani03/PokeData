package id.tiooooo.pokedata.ui.pages.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    screenModel: RegisterScreenModel,
) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }


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

            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    screenModel.dispatch(RegisterIntent.UpdateEmail(it))
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.emailError.isNotEmpty(),
                supportingText = {
                    if (state.emailError.isNotEmpty()) {
                        Text(
                            text = state.emailError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    screenModel.dispatch(RegisterIntent.UpdateName(it))
                },
                label = { Text("Nama") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.nameError.isNotEmpty(),
                supportingText = {
                    if (state.nameError.isNotEmpty()) {
                        Text(
                            text = state.nameError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdatePassword(it)) },
                label = { Text("Password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = state.passwordError.isNotEmpty(),
                supportingText = {
                    if (state.passwordError.isNotEmpty()) {
                        Text(
                            text = state.passwordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    val icon =
                        if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description =
                        if (isPasswordVisible) "Sembunyikan Password" else "Lihat Password"

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = { screenModel.dispatch(RegisterIntent.UpdateRePassword(it)) },
                label = { Text("Konfirmasi Password") },
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = state.confirmPasswordError.isNotEmpty(),
                supportingText = {
                    if (state.confirmPasswordError.isNotEmpty()) {
                        Text(
                            text = state.confirmPasswordError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    val icon =
                        if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description =
                        if (isConfirmPasswordVisible) "Sembunyikan Password" else "Lihat Password"

                    IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
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
