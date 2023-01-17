package br.com.brunocarvalhs.payflow.features.costs.payment_voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentPaymentVoucherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentVoucherFragment : BaseFragment<FragmentPaymentVoucherBinding>() {

    private val viewModel: PaymentVoucherViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentPaymentVoucherBinding =
        FragmentPaymentVoucherBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is PaymentVoucherViewState.Error -> this.showError(it.error)
                PaymentVoucherViewState.Loading -> this.loading()
                is PaymentVoucherViewState.Success -> this.navigateToCosts()
            }
        }
    }

    private fun navigateToCosts() {
        findNavController().popBackStack()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.name.editText?.setText(viewModel.cost.name)
        binding.prompt.editText?.setText(viewModel.cost.prompt)
        binding.value.editText?.setText(viewModel.cost.formatValue())
        binding.barcode.editText?.setText(viewModel.cost.barCode)
        binding.paymentVoucherUri.editText?.setText(viewModel.paymentVoucherUri)
        binding.registration.setOnClickListener { viewModel.save(generateCost()) }
    }

    override fun loading() {

    }

    private fun generateCost() = viewModel.cost.copy(
        paymentVoucher = binding.paymentVoucherUri.editText?.text.toString()
    )
}