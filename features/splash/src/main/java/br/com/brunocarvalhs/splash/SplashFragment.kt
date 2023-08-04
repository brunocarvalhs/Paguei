package br.com.brunocarvalhs.splash

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseComposeFragment
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.splash.ui.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseComposeFragment() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val viewModel: SplashViewModel by viewModels()

    @Composable
    override fun CreateScreen() = SplashScreen(this, viewModel)

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
