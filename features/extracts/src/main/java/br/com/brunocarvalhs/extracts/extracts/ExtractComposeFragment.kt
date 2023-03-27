package br.com.brunocarvalhs.extracts.extracts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragmentCompose
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractComposeFragment : BaseFragmentCompose() {

    private val viewModel: ExtractComposeViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ) = ComposeView(requireContext()).apply {
        setContent {
            PagueiTheme {
                ExtractLayout(viewModel = viewModel)
            }
        }
    }

    override fun initView() {
        visibilityToolbar(true)
    }
}