package br.com.brunocarvalhs.payflow.features.costs.selected_cost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.DialogCostsSelectedBinding
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsSelectedDialogFragment :
    BaseBottomSheetDialogFragment<DialogCostsSelectedBinding>() {

    private val viewModel: CostsSelectedViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogCostsSelectedBinding =
        DialogCostsSelectedBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        binding.content.text = requireContext().getString(
            R.string.selected_cost_description,
            viewModel.cost.name,
            viewModel.cost.value.toString()
        )
        binding.delete.setOnClickListener { viewModel.deleteCost() }
        binding.yes.setOnClickListener { navigateToPaymentVoucher(viewModel.cost) }
        binding.not.setOnClickListener { navigateToCancel() }
    }

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }

    private fun navigateToPaymentVoucher(cost: CostsEntities) {
        val action = CostsSelectedDialogFragmentDirections
            .actionItemListDialogFragmentToPaymentVoucherFragment(cost as CostsModel)
        findNavController().navigate(action)
    }

    private fun navigateToCancel() {
        findNavController().popBackStack()
    }
}