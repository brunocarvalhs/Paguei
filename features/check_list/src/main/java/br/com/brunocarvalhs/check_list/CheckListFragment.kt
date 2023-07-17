package br.com.brunocarvalhs.check_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.check_list.databinding.FragmentCheckListBinding
import br.com.brunocarvalhs.commons.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckListFragment : BaseFragment<FragmentCheckListBinding>() {

    private val viewModel: CheckListViewModel by viewModels()
    private val adapter by lazy { CheckListRecyclerViewAdapter(requireContext()) }

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
        val result = list.map { (key, value) ->
            CheckListRecyclerViewAdapter.CheckListData(
                name = key,
                values = value
            )
        }
        adapter.submitList(result)
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
}