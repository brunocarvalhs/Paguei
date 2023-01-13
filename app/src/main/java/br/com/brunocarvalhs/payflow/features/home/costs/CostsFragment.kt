package br.com.brunocarvalhs.payflow.features.home.costs

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.FragmentCostsListBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsFragment : BaseFragment<FragmentCostsListBinding>() {

    private val viewModel: CostsViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentCostsListBinding = FragmentCostsListBinding.inflate(inflater, container, false)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                CostsViewState.Loading -> this.loading()
                is CostsViewState.Success -> this.displayData(it.list)
                is CostsViewState.Error -> this.showError(it.message)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun displayData(list: List<CostsEntities>) {
        defineTotalCosts(list.size)
        binding.list.adapter = CostsRecyclerViewAdapter(list)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    @SuppressLint("StringFormatMatches")
    override fun initView() {
        this.visibilityToolbar(true)
        binding.list.layoutManager = LinearLayoutManager(context)
        viewModel.user?.let {
            Glide.with(this).load(it.photoUrl).centerCrop().into(binding.avatar)
            binding.name.text =
                requireActivity().getString(R.string.home_title_header, it.fistName())
        }
    }

    override fun loading() {
        this.defineTotalCosts(0)
    }

    private fun defineTotalCosts(total: Int) {
        binding.textTotalCosts.text =
            requireActivity().getString(R.string.costs_total_text, total.toString())
    }
}