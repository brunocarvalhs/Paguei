package br.com.brunocarvalhs.auth.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import br.com.brunocarvalhs.auth.LoginPreviewParameterProvider
import br.com.brunocarvalhs.auth.LoginViewModel
import br.com.brunocarvalhs.auth.LoginViewState
import br.com.brunocarvalhs.auth.R
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract

@Composable
fun LoginScreen(
    fragment: Fragment, modifier: Modifier = Modifier, viewModel: LoginViewModel
) {
    val signInLauncher =
        rememberLauncherForActivityResult(contract = FirebaseAuthUIActivityResultContract()) {
            viewModel.onSignInResult()
        }

    val uiState = viewModel.state.collectAsState()

    when (uiState.value) {
        LoginViewState.Success -> viewModel.navigateToHome(fragment)
        else -> LoginContent(
            uiState = uiState.value,
            modifier = modifier,
            onLogin = { viewModel.signIn(signInLauncher) },
        )
    }
}

@Composable
fun LoginContent(
    uiState: LoginViewState,
    modifier: Modifier,
    onLogin: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f)
                .background(MaterialTheme.colorScheme.primary)
                .clip(MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        Button(
            onClick = onLogin, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            when (uiState) {
                is LoginViewState.Loading -> CircularProgressIndicator()
                else -> Text(text = stringResource(id = R.string.login_button_text))
            }
        }
    }
}

@Composable
@Preview
fun PreviewLoginScreen(
    @PreviewParameter(LoginPreviewParameterProvider::class) uiState: LoginViewState
) {
    PagueiTheme {
        LoginContent(uiState = uiState, onLogin = {}, modifier = Modifier)
    }
}