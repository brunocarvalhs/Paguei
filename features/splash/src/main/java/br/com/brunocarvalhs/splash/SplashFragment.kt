package br.com.brunocarvalhs.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            SplashFragment::class.simpleName.orEmpty(), SplashFragment::class
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                SplashViewState.Loading -> this.visibilityLoading()
                SplashViewState.Session -> navigateToHome()
                SplashViewState.NotSession -> navigateToLogin()
            }
        }
    }

    private fun visibilityLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun navigateToLogin() {
        binding.loading.visibility = View.GONE
        val request = navigation.navigateToLoginRegister()
        findNavController().navigate(request)
    }

    private fun navigateToHome() {
        binding.loading.visibility = View.GONE
        val request = navigation.navigateToCosts()
        findNavController().navigate(request)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {}

    override fun onResume() {
        super.onResume()
        viewModel.onSession()
    }
}
