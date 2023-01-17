package br.com.brunocarvalhs.paguei.features.billet_registration.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.paguei.databinding.DialogBilletRegistrationBinding

class BilletRegistrationDialogFragment :
    BaseBottomSheetDialogFragment<DialogBilletRegistrationBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): DialogBilletRegistrationBinding =
        DialogBilletRegistrationBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        binding.tryNew.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.enterCode.setOnClickListener {
            val action = BilletRegistrationDialogFragmentDirections
                .actionBilletRegistrationDialogFragmentToBilletRegistrationFormFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }
}