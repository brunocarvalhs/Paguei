package br.com.brunocarvalhs.calculation.cost_resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationCostResumeBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.toMoney
import br.com.brunocarvalhs.domain.entities.CostEntities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculationCostResumeFragment : BaseFragment<FragmentCalculationCostResumeBinding>() {

    private val viewModel: CalculationCostResumeViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCalculationCostResumeBinding =
        FragmentCalculationCostResumeBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CalculationCostResumeViewState.Error -> this.showError(it.message)
                CalculationCostResumeViewState.Loading -> this.loading()
                is CalculationCostResumeViewState.Success -> this.displayData(it.list)
            }
        }
    }

    private fun displayData(list: List<CostEntities>) {
        binding.add.isEnabled = list.isNotEmpty()
        binding.totalValueCosts.text = viewModel.totalCosts?.toMoney()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        binding.add.isEnabled = false
        binding.add.setOnClickListener { this.navigateToCalculationResume() }
    }

    private fun navigateToCalculationResume() {
        val action = CalculationCostResumeFragmentDirections
            .actionCalculationCostResumeFragmentToCalculationResumeFragment(
                totalSalary = viewModel.totalSalary,
                totalCosts = viewModel.totalCosts.orEmpty(),
                members = viewModel.listMembers
            )
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}