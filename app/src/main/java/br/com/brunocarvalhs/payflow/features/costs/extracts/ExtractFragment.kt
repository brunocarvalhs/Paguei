package br.com.brunocarvalhs.payflow.features.costs.extracts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentExtractListBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtractFragment : BaseFragment<FragmentExtractListBinding>(),
    ExtractRecyclerViewAdapter.ExtractClickListener {

    private val viewModel: ExtractViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
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
        binding.list.layoutManager = LinearLayoutManager(context)
    }

    override fun loading() {

    }

    private fun displayData(list: List<CostsEntities>) {
        binding.list.adapter = ExtractRecyclerViewAdapter(requireContext(), list, this)
    }

    override fun onClick(cost: CostsEntities) {

    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }
}