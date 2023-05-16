package br.com.brunocarvalhs.extracts.extracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.extracts.databinding.FragmentExtractListBinding
import com.google.android.material.search.SearchView.TransitionState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractFragment : BaseFragment<FragmentExtractListBinding>(),
    ExtractRecyclerViewAdapter.ExtractClickListener {

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
        binding.searchView.editText.apply {
            doOnTextChanged { text, _, _, _ ->
                adapter.filter(text.toString())
            }
        }
        binding.searchView.addTransitionListener { _, _, newState ->
            val state = newState === TransitionState.HIDDEN
            visibilityToolbar(state)
            if (state) adapter.filter(String())
        }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        binding.listSeachr.adapter = adapter
        binding.listSeachr.layoutManager = LinearLayoutManager(context)
    }

    private fun displayData(list: List<CostEntities>) = adapter.submitList(list)

    override fun onClick(cost: CostEntities) {
        val action = ExtractFragmentDirections.actionExtractFragmentToExtractReaderFragment(cost)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }
}