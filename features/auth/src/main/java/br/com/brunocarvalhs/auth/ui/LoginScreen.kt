package br.com.brunocarvalhs.auth.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
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

    when (val state = uiState.value) {
        is LoginViewState.Error -> Toast.makeText(
            fragment.requireContext(),
            state.message,
            Toast.LENGTH_LONG
        ).show()

        LoginViewState.Success -> viewModel.navigateToHome(fragment)
        else -> LoginContent(
            modifier = modifier,
            onLogin = { viewModel.signIn(signInLauncher) },
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier,
    onLogin: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    Column(
        verticalArrangement = Arrangement.SpaceBetween, modifier = modifier.fillMaxSize()
    ) {

        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = null,
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Button(
                onClick = onLogin, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.login_button_text))
            }
        }
    }
}

@Composable
@Preview
@Preview(device = "spec:parent=pixel_5,orientation=landscape")
fun PreviewLoginScreen() {
    PagueiTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            LoginContent(onLogin = {}, modifier = Modifier)
        }
    }
}