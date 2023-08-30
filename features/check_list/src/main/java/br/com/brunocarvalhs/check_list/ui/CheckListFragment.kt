package br.com.brunocarvalhs.check_list.ui

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseComposeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckListFragment : BaseComposeFragment() {


    private val viewModel: CheckListViewModel by viewModels()

    override fun initView() {
        this.visibilityToolbar(true)
    }

    @Composable
    override fun CreateScreen() =
        CheckListScreen(viewModel = viewModel, navController = findNavController())

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}