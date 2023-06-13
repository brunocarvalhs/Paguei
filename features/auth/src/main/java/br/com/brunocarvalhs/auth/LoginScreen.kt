package br.com.brunocarvalhs.auth.fragments

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.brunocarvalhs.auth.R
import br.com.brunocarvalhs.auth.states.LoginViewState
import br.com.brunocarvalhs.auth.viewmodels.ILoginViewModel
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.commons.theme.SeedRed
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
internal fun LoginScreen(
    navController: NavController,
    viewModel: ILoginViewModel = viewModel()
) {
    val signInLauncher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract()
    ) { viewModel.onSignInResult() }

    val state by viewModel.stateCompose.collectAsState()

    LoginContent(state, navController)
}

@Composable
private fun LoginContent(
    state: LoginViewState,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(SeedRed)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                    modifier = Modifier.height(400.dp)
                )
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            when (state) {
                LoginViewState.Loading -> {
                    CircularProgressIndicator()
                }
                LoginViewState.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate("home")
                    }
                }
                is LoginViewState.Error -> {
                    Text(
                        text = state.message.orEmpty(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                LoginViewState.Idle -> {
                    Text(
                        text = stringResource(id = R.string.login_button_text),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

object EmptyPreviewParameterProvider : PreviewParameterProvider<Any> {
    override val values: Sequence<Any> = emptySequence()
}

@Preview(showBackground = true, device = Devices.DEFAULT, showSystemUi = true)
@Composable
fun LoginScreenPreview(
    previewParameterProvider: Sequence<Any> = EmptyPreviewParameterProvider.values
) {
    val viewModel = object : ILoginViewModel {
        private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
        override val stateCompose: StateFlow<LoginViewState> = _state.asStateFlow()

        override fun onSignInResult() {
            // do nothing
        }

        override fun onSession() {
            // do nothing
        }

        override fun startSignInActivity(launcher: ActivityResultLauncher<Intent>) {
            // do nothing
        }
    }
    PagueiTheme {
        LoginScreen(navController = rememberNavController(), viewModel = viewModel)
    }
}
