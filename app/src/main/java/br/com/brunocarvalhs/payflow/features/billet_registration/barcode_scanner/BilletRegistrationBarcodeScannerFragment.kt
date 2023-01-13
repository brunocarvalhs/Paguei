package br.com.brunocarvalhs.payflow.features.billet_registration.barcode_scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentBilletRegistrationBarcodeScannerBinding

class BilletRegistrationBarcodeScannerFragment :
    BaseFragment<FragmentBilletRegistrationBarcodeScannerBinding>() {

    private val viewModel: BilletRegistrationBarcodeScannerViewModel by viewModels()

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBilletRegistrationBarcodeScannerBinding.inflate(inflater, container, false)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {

    }

    override fun loading() {

    }


}