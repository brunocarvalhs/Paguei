package br.com.brunocarvalhs.paguei.features.costs.selected_cost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.DialogCostsSelectedBinding
import br.com.brunocarvalhs.domain.entities.CostsEntities
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsSelectedDialogFragment : BaseBottomSheetDialogFragment<DialogCostsSelectedBinding>() {

    private val viewModel: CostsSelectedViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogCostsSelectedBinding =
        DialogCostsSelectedBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        binding.content.text = requireContext().getString(
            R.string.selected_cost_description, viewModel.cost.name, viewModel.cost.value.toString()
        )
        binding.delete.setOnClickListener { questionDelete() }
        binding.yes.setOnClickListener { navigateToPaymentVoucher(viewModel.cost) }
        binding.not.setOnClickListener { navigateToCancel() }
    }

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    private fun questionDelete() {
        MaterialAlertDialogBuilder(
            requireContext(),
        ).setTitle("Confirmação de remoção.")
            .setMessage("Deseja realemente deletar o \nboleto ${viewModel.cost.name} do valor de ${viewModel.cost.value}")
            .setNegativeButton("Não") { _, _ ->
                findNavController().popBackStack(R.id.costsFragment, inclusive = false)
            }.setPositiveButton("Sim") { _, _ ->
                viewModel.deleteCost()
            }.show()
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