package br.com.brunocarvalhs.auth

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class LoginPreviewParameterProvider : PreviewParameterProvider<LoginViewState> {
    override val values: Sequence<LoginViewState>
        get() = sequenceOf(
            LoginViewState.Default,
            LoginViewState.Loading,
            LoginViewState.Success,
            LoginViewState.Error("Error message")
        )

}
