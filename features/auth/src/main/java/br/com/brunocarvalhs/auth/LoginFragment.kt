package br.com.brunocarvalhs.auth

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.auth.ui.LoginScreen
import br.com.brunocarvalhs.commons.BaseComposeFragment
import br.com.brunocarvalhs.domain.services.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseComposeFragment() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var analyticsService: AnalyticsService

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
