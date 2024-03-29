package br.com.brunocarvalhs.check_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.check_list.databinding.FragmentCheckListBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckListFragment : BaseFragment<FragmentCheckListBinding>(),
    CheckListRecyclerViewAdapter.Listeners {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: CheckListViewModel by viewModels()
    private val adapter by lazy { CheckListRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCheckListBinding =
        FragmentCheckListBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CheckListViewState.Error -> this.showError(it.message)
                CheckListViewState.Loading -> this.loading()
                is CheckListViewState.Success -> this.displayData(it.list)
            }
        }
    }

    private fun displayData(list: HashMap<String, Map<String?, Boolean>>) {
        adapter.submitList(list)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        this.setupList()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun onSelect(name: String?) {
        val action = navigation.navigateToBilletRegistrationForm()
        findNavController().navigate(action)
    }
}