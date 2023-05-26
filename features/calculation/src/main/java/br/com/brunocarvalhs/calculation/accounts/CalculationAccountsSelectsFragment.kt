package br.com.brunocarvalhs.calculation.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationAccountsSelectsBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.moneyToDouble
import br.com.brunocarvalhs.domain.entities.UserEntities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculationAccountsSelectsFragment :
    BaseFragment<FragmentCalculationAccountsSelectsBinding>(),
    CalculationAccountsMembersRecyclerViewAdapter.CalculationAccountsMembersClickListener {

    private val viewModel: CalculationAccountsSelectsViewModel by viewModels()
    private val adapter by lazy {
        CalculationAccountsMembersRecyclerViewAdapter(
            requireContext(), this
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCalculationAccountsSelectsBinding =
        FragmentCalculationAccountsSelectsBinding.inflate(
            inflater, container, attachToParent
        )

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CalculationAccountsSelectsViewState.Error -> this.showError(it.error)
                CalculationAccountsSelectsViewState.Loading -> this.loading()
                is CalculationAccountsSelectsViewState.Success -> this.displayData(it.list)
            }
        }
    }

    private fun displayData(list: List<UserEntities?>) {
        adapter.submitList(list)
        binding.add.isEnabled = list.isNotEmpty()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupList()
        binding.add.isEnabled = false
        binding.add.setOnClickListener { this.navigationToCalculationCost() }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    private fun navigationToCalculationCost() {
        val list = viewModel.listCosts.toTypedArray()
        val total = viewModel.totalSalary.get().orEmpty()
        val members = viewModel.listMembers.toTypedArray()
        val action =
            CalculationAccountsSelectsFragmentDirections
                .actionCalculationAccountsSelectsFragmentToCalculationCostResumeFragment(
                    costs = list, totalSalary = total, members = members
                )
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onLongClickListener(list: MutableList<UserEntities?>): Boolean {
        viewModel.replaceCalculation(list)
        binding.add.isEnabled = list.isNotEmpty()
        return true
    }
}