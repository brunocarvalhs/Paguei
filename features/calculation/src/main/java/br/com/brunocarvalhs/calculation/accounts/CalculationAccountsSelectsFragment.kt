package br.com.brunocarvalhs.calculation.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.calculation.R
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationAccountsSelectsBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.AdsService
import br.com.brunocarvalhs.domain.services.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculationAccountsSelectsFragment :
    BaseFragment<FragmentCalculationAccountsSelectsBinding>(),
    CalculationAccountsMembersRecyclerViewAdapter.CalculationAccountsMembersClickListener {

    private val viewModel: CalculationAccountsSelectsViewModel by viewModels()

    @Inject
    lateinit var analyticsService: AnalyticsService

    @Inject
    lateinit var adsService: AdsService

    private val adapter by lazy {
        CalculationAccountsMembersRecyclerViewAdapter(requireContext(), this)
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCalculationAccountsSelectsBinding =
        FragmentCalculationAccountsSelectsBinding.inflate(
            inflater, container, attachToParent
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CalculationAccountsSelectsFragment::class.simpleName.orEmpty(),
            CalculationAccountsSelectsFragment::class
        )
        adsService.initFullBanner(getString(R.string.full_banner))
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CalculationAccountsSelectsViewState.Error -> this.showError(it.error)
                CalculationAccountsSelectsViewState.Loading -> this.loading()
                is CalculationAccountsSelectsViewState.Success -> this.displayData(it.list)
            }
        }
    }

    private fun displayData(list: List<UserEntities>) {
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
            this.navigationToCalculationCost()
            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "next")),
                CalculationAccountsSelectsFragment::class
            )
        }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    private fun navigationToCalculationCost() {
        val list = viewModel.listCosts.toTypedArray()
        val total = viewModel.totalSalary.get().orEmpty()
        val members = viewModel.listMembersSelected.toTypedArray()
        if (list.isNotEmpty() && total.isNotEmpty() && members.isNotEmpty()) {
            val action =
                CalculationAccountsSelectsFragmentDirections
                    .actionCalculationAccountsSelectsFragmentToCalculationCostResumeFragment(
                        costs = list, totalSalary = total, members = members
                    )
            findNavController().navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onLongClickListener(user: MutableList<UserEntities>): Boolean {
        viewModel.replaceCalculation(user)
        binding.add.isEnabled = user.isNotEmpty()

        analyticsService.trackEvent(
            AnalyticsService.Events.MEMBER_LONG_CLICKED,
            mapOf("user_list_size" to user.size.toString()),
            CalculationAccountsSelectsFragment::class
        )
        return true
    }
}