package br.com.brunocarvalhs.costs.costs_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.costs.databinding.FragmentCostsListBinding
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CostsFragment : BaseFragment<FragmentCostsListBinding>(),
    CostsRecyclerViewAdapter.CostClickListener {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val viewModel: CostsViewModel by viewModels()
    private val adapter by lazy { CostsRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCostsListBinding =
        FragmentCostsListBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                CostsViewState.Loading -> this.loading()
                is CostsViewState.Success -> this.displayData(it.list)
                is CostsViewState.Error -> this.showError(it.message)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CostsFragment::class.simpleName.orEmpty(),
            CostsFragment::class
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onResume() {
        super.onResume()
        findNavController().clearBackStack(R.id.costsFragment)
    }

    private fun displayData(list: List<CostEntities>) {
        setupHeader(list)
        defineTotalCosts(list.size)
        adapter.submitList(list)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.defineAppNavigation(R.id.costsFragment)
        this.visibilityToolbar(true)
        this.setupHeader()
        this.setupNavigation()
        this.setupList()
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
        ItemTouchHelper(adapter.simpleItemTouchCallback)
            .attachToRecyclerView(binding.list)
    }

    private fun defineTotalCosts(total: Int = 0) {
        binding.textTotalCosts.text =
            requireActivity().getString(R.string.costs_total_text, total.toString())
    }

    private fun setupHeader(list: List<CostEntities>? = null) {
        viewModel.header.let {
            binding.avatarContainer.visibility = if (it.isGroup) View.GONE else View.VISIBLE
            if (it.photoUrl.isNullOrEmpty()) {
                binding.avatar.visibility = View.GONE
                binding.avatarText.visibility = View.VISIBLE
                binding.avatarText.text = it.initials
            } else {
                Glide.with(this).load(it.photoUrl).centerCrop().into(binding.avatar)
            }

            binding.name.text =
                if (it.isGroup) requireActivity().getString(
                    R.string.home_title_group_header,
                    it.name
                )
                else requireActivity().getString(R.string.home_title_header, it.name)

            if (!it.isGroup) {
                binding.avatar.setOnClickListener { navigateToProfile() }
                binding.name.setOnClickListener { navigateToProfile() }
            }
            binding.cadastrados.setOnClickListener { navigateToReport() }

            binding.bottomAppBar.menu.findItem(R.id.calculationFragment).isVisible =
                it.isGroup && list?.isNotEmpty() == true
        }
    }

    private fun navigateToReport() {
        val action = navigation.navigateToReport()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.REPORT_SCREEN_VIEWED,
            mapOf(),
            CostsFragment::class
        )
    }

    private fun setupNavigation() {
        binding.add.setOnClickListener { navigateToAddCosts() }
        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.extractFragment -> navigateToExtracts()
                R.id.groupsFragment -> navigateToGroups()
                R.id.calculationFragment -> navigateToCalculation()
                R.id.checkListFragment -> navigateToCheckList()
                else -> false
            }
        }
    }

    private fun navigateToCheckList(): Boolean {
        val action = navigation.navigateToCheckList()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.CHECK_LIST_MENU_SELECTED,
            mapOf(),
            CostsFragment::class
        )

        return true
    }

    private fun navigateToCalculation(): Boolean {
        val action = navigation.navigateToCalculation()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.CALCULATION_MENU_SELECTED,
            mapOf(),
            CostsFragment::class
        )

        return true
    }

    private fun navigateToProfile() {
        val request = navigation.navigateToProfileRegister()
        findNavController().navigate(request)
    }

    private fun navigateToExtracts(): Boolean {
        val action = navigation.navigateToExtractsList()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.EXTRACTS_MENU_SELECTED,
            mapOf(),
            CostsFragment::class
        )

        return true
    }

    private fun navigateToGroups(): Boolean {
        val action = navigation.navigateToGroups()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.EXTRACTS_MENU_SELECTED,
            mapOf(),
            CostsFragment::class
        )

        return true
    }

    private fun navigateToReaderCost(cost: CostEntities) {
        val action = CostsFragmentDirections.actionCostsFragmentToCostReaderFragment(cost)
        findNavController().navigate(action)
    }


    private fun navigateToAddCosts() {
        val action = navigation.navigateToBilletRegistrationForm()
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.ADD_COST_BUTTON_CLICKED,
            mapOf(),
            CostsFragment::class
        )
    }

    override fun onClick(cost: CostEntities) {
        navigateToReaderCost(cost)

        analyticsService.trackEvent(
            AnalyticsService.Events.COST_ITEM_CLICKED,
            mapOf(Pair("cost_id", cost.id)),
            CostsFragment::class
        )
    }

    override fun onLongClick(cost: CostEntities): Boolean {
        val action =
            CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(cost)
        findNavController().navigate(action)

        analyticsService.trackEvent(
            AnalyticsService.Events.COST_ITEM_LONG_CLICKED,
            mapOf(Pair("cost_id", cost.id)),
            CostsFragment::class
        )

        return true
    }

    override fun onSwipeLeft(costEntities: CostEntities, position: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.question_delete_title))
            .setMessage(
                getString(
                    R.string.question_delete_message,
                    costEntities.name,
                    costEntities.value
                )
            )
            .setNegativeButton(getString(R.string.question_delete_negative_text)) { _, _ ->
                adapter.notifyDataSetChanged()
            }.setPositiveButton(getString(R.string.question_delete_positive_text)) { _, _ ->
                viewModel.deleteCost(costEntities) {
                    adapter.removeItem(position)
                    defineTotalCosts(adapter.values.size)
                }

                analyticsService.trackEvent(
                    AnalyticsService.Events.CLICK_EVENT,
                    mapOf(Pair("event_name", "delete_cost")),
                    CostsFragment::class
                )
            }.show()
    }

    override fun onSwipeRight(costEntities: CostEntities, position: Int) {
        val action =
            CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(costEntities)
        findNavController().navigate(action)

    }
}