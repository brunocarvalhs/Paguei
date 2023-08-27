package br.com.brunocarvalhs.costs.ui.costs_list

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseComposeFragment
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CostsFragment : BaseComposeFragment() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val viewModel: CostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CostsFragment::class.simpleName.orEmpty(), CostsFragment::class
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onResume() {
        super.onResume()
        findNavController().clearBackStack(R.id.costsFragment)
    }

    override fun initView() {
        this.defineAppNavigation(R.id.costsFragment)
        this.visibilityToolbar(true)
    }

    @Composable
    override fun CreateScreen() =
        CostsScreen(navController = this.findNavController(), viewModel = viewModel)
}