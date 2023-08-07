package br.com.brunocarvalhs.profile.read.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.profile.R
import br.com.brunocarvalhs.profile.read.ProfileViewModel
import br.com.brunocarvalhs.profile.read.ProfileViewState
import coil.compose.AsyncImage

@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current
    when (val state = uiState.value) {
        else -> {
            ProfileContent(uiState = state,
                onLogout = { viewModel.logout(navController) },
                onSettings = { viewModel.navigateToSettings(navController) },
                onEditProfile = { viewModel.navigateToEditProfile(navController) },
                onQrCode = { viewModel.visibilityQrCode(context) })
        }
    }
}

@Composable
private fun ProfileContent(
    uiState: ProfileViewState,
    onLogout: () -> Unit,
    onSettings: () -> Unit,
    onEditProfile: () -> Unit,
    onQrCode: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.padding(bottom = 20.dp)
            ) {

                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .offset(y = 50.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        if (uiState is ProfileViewState.Success) {
                            if (uiState.user?.photoUrl != null) {
                                AsyncImage(
                                    model = uiState.user.photoUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .size(200.dp)
                                        .clip(CircleShape)
                                )
                            } else {
                                Text(
                                    text = uiState.user?.initialsName().orEmpty(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp)
                                )
                            }
                        }
                    }
                }

            }
            Row(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                if (uiState is ProfileViewState.Success) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = uiState.user?.name.orEmpty(),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 26.dp)
                        )

                        Text(
                            text = uiState.user?.email.orEmpty(),
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = onQrCode, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.fragment_profile_qr_code_button_text))
                    }
                    Divider()
                    TextButton(
                        onClick = onEditProfile, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.fragment_profile_edit_profile_button_text))
                    }
                    Divider()
                    TextButton(
                        onClick = onSettings, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.fragment_profile_settings_button_text))
                    }
                    Divider()
                    TextButton(
                        onClick = onLogout, modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.fragment_profile_logout_button_text))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewProfileScreen() {
    PagueiTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ProfileContent(uiState = ProfileViewState.Default,
                onLogout = { },
                onSettings = { },
                onEditProfile = { },
                onQrCode = { })
        }
    }
}