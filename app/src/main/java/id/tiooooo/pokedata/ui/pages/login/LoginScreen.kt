package id.tiooooo.pokedata.ui.pages.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.pokedata.base.BaseScaffold
import id.tiooooo.pokedata.ui.pages.register.RegisterRoute
import id.tiooooo.pokedata.ui.pages.splash.SplashRoute

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    screenModel: LoginScreenModel,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by screenModel.state.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    if (state.isLoading) {
        CircularProgressIndicator()
    }

    if (state.errorMessage != null) {
        Text(text = state.errorMessage!!, color = Color.Red)
    }

    if (state.isLoginSuccess) {
        LaunchedEffect(Unit) {
            navigator.replace(SplashRoute())
        }
    }

    BaseScaffold(
        modifier = modifier,
    ) {
        Column {
            Spacer(Modifier.height(50.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = email,
                onValueChange = {
                    email = it
                }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                value = password,
                onValueChange = {
                    password = it
                }
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = { screenModel.executeLogin(email, password) }
            ) {
                Text(
                    text = "Login"
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {navigator.push(RegisterRoute()) }
            ) {
                Text(
                    text = "Register"
                )
            }
        }
    }
}