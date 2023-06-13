package br.com.brunocarvalhs.calculation.cost_resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationCostResumeBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculationCostResumeFragment : BaseFragment<FragmentCalculationCostResumeBinding>() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val viewModel: CalculationCostResumeViewModel by viewModels()
    private val adapter by lazy { CalculationCostsRecyclerViewAdapter(requireContext()) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCalculationCostResumeBinding =
        FragmentCalculationCostResumeBinding.inflate(inflater, container, attachToParent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CalculationCostResumeFragment::class.simpleName.orEmpty(),
            CalculationCostResumeFragment::class
        )
    }

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
        binding.add.setOnClickListener {
            this.navigateToCalculationResume()

            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "next")),
                CalculationCostResumeFragment::class
            )
        }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    private fun navigateToCalculationResume() {
        val action = CalculationCostResumeFragmentDirections
            .actionCalculationCostResumeFragmentToCalculationResumeFragment(
                totalSalary = viewModel.totalSalary,
                totalCosts = viewModel.totalCosts.get().orEmpty(),
                members = viewModel.listMembers
            )
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}