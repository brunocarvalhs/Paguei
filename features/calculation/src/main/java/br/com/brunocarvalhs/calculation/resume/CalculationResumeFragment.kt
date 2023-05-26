package br.com.brunocarvalhs.calculation.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationCostResumeBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.UserEntities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculationResumeFragment : BaseFragment<FragmentCalculationCostResumeBinding>() {

    private val viewModel: CalculationResumeViewModel by viewModels()
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCalculationCostResumeBinding =
        FragmentCalculationCostResumeBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CalculationResumeViewState.Error -> this.showError(it.message)
                CalculationResumeViewState.Loading -> this.loading()
                is CalculationResumeViewState.Success -> this.displayData(it.percentagesToMembers)
            }
        }
    }

    private fun displayData(percentagesToMembers: HashMap<UserEntities, Double>) {
        binding.totalValueCosts.text =
            percentagesToMembers.map { Pair(it.key.firstName(), it.value) }.toMap().toString()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}