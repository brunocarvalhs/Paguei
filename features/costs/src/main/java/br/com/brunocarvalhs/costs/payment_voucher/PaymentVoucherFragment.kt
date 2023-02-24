package br.com.brunocarvalhs.costs.payment_voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.costs.databinding.FragmentPaymentVoucherBinding
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.paguei.features.costs.payment_voucher.PaymentVoucherViewState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentVoucherFragment : BaseFragment<FragmentPaymentVoucherBinding>() {

    @Inject
    lateinit var navigation: Navigation

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
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.registration.setOnClickListener { viewModel.savePaymentVoucher() }
    }

    override fun loading() {

    }
}