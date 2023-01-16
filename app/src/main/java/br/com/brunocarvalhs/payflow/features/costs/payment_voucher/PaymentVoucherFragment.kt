package br.com.brunocarvalhs.payflow.features.costs.payment_voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
    }

    override fun loading() {

    }
}