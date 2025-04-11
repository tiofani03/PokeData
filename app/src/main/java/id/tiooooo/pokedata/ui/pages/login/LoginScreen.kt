package id.tiooooo.pokedata.ui.pages.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.pages.dashboard.DashboardRoute
import id.tiooooo.pokedata.ui.pages.register.RegisterRoute
import id.tiooooo.pokedata.ui.theme.MEDIUM_PADDING

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    screenModel: LoginScreenModel,
) {
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.NavigateToHome -> { navigator.replaceAll(DashboardRoute()) }
                LoginEffect.NavigateToRegister -> navigator.push(RegisterRoute())
            }
        }
    }

    BaseScaffold(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = state.email,
                onValueChange = { screenModel.dispatch(LoginIntent.UpdateEmail(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { screenModel.dispatch(LoginIntent.UpdatePassword(it)) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error != null) {
                Text(text = state.error.orEmpty(), color = Color.Red)
            }

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Button(
                onClick = { screenModel.dispatch(LoginIntent.ExecuteLogin) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isButtonEnable
            ) {
                Text(if (state.isLoading) "Loading..." else "Login")
            }

            TextButton(
                onClick = { screenModel.dispatch(LoginIntent.NavigateToRegister) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Belum punya akun? Register")
            }
        }
    }
}