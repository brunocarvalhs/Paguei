package br.com.brunocarvalhs.paguei.features.costs.extracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.paguei.databinding.FragmentExtractListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractFragment : BaseFragment<FragmentExtractListBinding>(),
    ExtractRecyclerViewAdapter.ExtractClickListener,
    SearchView.OnQueryTextListener {

    private val viewModel: ExtractViewModel by viewModels()

    private val adapter by lazy { ExtractRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentExtractListBinding =
        FragmentExtractListBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                ExtractViewState.Loading -> this.loading()
                is ExtractViewState.Success -> this.displayData(it.list)
                is ExtractViewState.Error -> this.showError(it.message)
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(true)
        this.setupList()
        this.setupSearch()
    }

    private fun setupSearch() {
        binding.search.setOnQueryTextListener(this)
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun loading() {

    }

    private fun displayData(list: List<CostsEntities>) {
        adapter.submitList(list)
    }

    override fun onClick(cost: CostsEntities) {
        val action = ExtractFragmentDirections
            .actionExtractFragmentToExtractReaderFragment(cost as CostsModel)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.getFilter().filter(newText)
        return false
    }
}