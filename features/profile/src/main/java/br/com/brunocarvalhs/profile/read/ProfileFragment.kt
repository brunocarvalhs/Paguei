package br.com.brunocarvalhs.profile.read

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseComposeFragment
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.profile.read.ui.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseComposeFragment() {

    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var analyticsService: AnalyticsService

    @Composable
    override fun CreateScreen() = ProfileScreen(this.findNavController(), viewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            ProfileFragment::class.simpleName.orEmpty(),
            ProfileFragment::class
        )
    }

    override fun initView() {
        this.visibilityToolbar(true)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}