package br.com.brunocarvalhs.splash.feature.presentation.content

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseComposeFragment
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.splash.SplashSession
import br.com.brunocarvalhs.splash.commons.providers.SplashAnalyticsProvider
import br.com.brunocarvalhs.splash.feature.presentation.content.ui.SplashScreen
import br.com.brunocarvalhs.splash.feature.presentation.viewmodel.SplashViewModel
import br.com.brunocarvalhs.splash.feature.presentation.viewmodel.SplashViewModelFactory

class SplashFragment : BaseComposeFragment() {

    private val analyticsService: SplashAnalyticsProvider = SplashSession.dependencies.analytics

    private val viewModel: SplashViewModel by viewModels(
        factoryProducer = { SplashViewModelFactory() }
    )

    @Composable
    override fun CreateScreen() = SplashScreen(this.findNavController(), viewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            SplashFragment::class.simpleName.orEmpty(), SplashFragment::class
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.onSession()
    }
}
