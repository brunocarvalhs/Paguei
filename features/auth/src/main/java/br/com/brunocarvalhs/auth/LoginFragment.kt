package br.com.brunocarvalhs.auth

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.viewModels
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
    override fun createScreen(): Unit =
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            LoginScreen(
                fragment = this,
                viewModel = viewModel
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            LoginFragment::class.simpleName.orEmpty(),
            LoginFragment::class
        )
    }
}
