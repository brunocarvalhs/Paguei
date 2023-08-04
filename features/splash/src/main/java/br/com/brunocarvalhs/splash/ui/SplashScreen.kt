package br.com.brunocarvalhs.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.splash.R
import br.com.brunocarvalhs.splash.SplashViewModel
import br.com.brunocarvalhs.splash.SplashViewState

@Composable
fun SplashScreen(fragment: Fragment, viewModel: SplashViewModel) {
    val uiState = viewModel.state.collectAsState()

    when (uiState.value) {
        SplashViewState.Loading -> SplashContent()
        SplashViewState.Session -> viewModel.navigateToHome(fragment)
        SplashViewState.NotSession -> viewModel.navigateToLogin(fragment)
    }
}

@Composable
private fun SplashContent() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scale(1f)
        )

        CircularProgressIndicator()

    }
}

@Composable
@Preview
private fun PreviewSplashScreen() {
    PagueiTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SplashContent()
        }
    }
}