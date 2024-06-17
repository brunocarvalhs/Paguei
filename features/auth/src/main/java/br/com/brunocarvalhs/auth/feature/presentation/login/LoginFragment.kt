package br.com.brunocarvalhs.auth.feature.presentation.login

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.auth.AuthSession
import br.com.brunocarvalhs.auth.feature.presentation.viewmodel.LoginViewModel
import br.com.brunocarvalhs.auth.feature.presentation.viewmodel.LoginViewModelFactory
import br.com.brunocarvalhs.commons.BaseComposeFragment

internal class LoginFragment : BaseComposeFragment() {

    private val viewModel: LoginViewModel by viewModels(
        factoryProducer = { LoginViewModelFactory() }
    )

    private var analyticsService = AuthSession.dependencies.analytics

    @Composable
    override fun CreateScreen(): Unit = LoginScreen(
        navController = this.findNavController(),
        viewModel = viewModel
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            LoginFragment::class.simpleName.orEmpty(), LoginFragment::class
        )
    }
}
