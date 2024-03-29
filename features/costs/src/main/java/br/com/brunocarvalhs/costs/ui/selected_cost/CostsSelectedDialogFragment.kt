package br.com.brunocarvalhs.costs.ui.selected_cost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.costs.databinding.DialogCostsSelectedBinding
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CostsSelectedDialogFragment : BaseBottomSheetDialogFragment<DialogCostsSelectedBinding>() {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: CostsSelectedViewModel by viewModels()

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CostsSelectedDialogFragment::class.simpleName.orEmpty(),
            CostsSelectedDialogFragment::class
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogCostsSelectedBinding =
        DialogCostsSelectedBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        binding.content.text = requireContext().getString(
            R.string.selected_cost_description, viewModel.cost.name, viewModel.cost.value.toString()
        )
        binding.yes.setOnClickListener { navigateToPaymentVoucher(viewModel.cost) }
        binding.not.setOnClickListener { navigateToCancel() }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CostsSelectedViewState.Error -> this.showError(it.message)
                CostsSelectedViewState.Loading -> this.loading()
                CostsSelectedViewState.Success -> this.successDelete()
            }
        }
    }

    private fun successDelete() {
        val action = navigation.navigateToCosts()
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    private fun navigateToPaymentVoucher(cost: CostEntities) {
        val action =
            CostsSelectedDialogFragmentDirections.actionItemListDialogFragmentToPaymentVoucherFragment(
                cost
            )
        findNavController().navigate(action)
    }

    private fun navigateToCancel() {
        findNavController().popBackStack()
    }
}