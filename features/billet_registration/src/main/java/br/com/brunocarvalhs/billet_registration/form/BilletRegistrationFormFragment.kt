package br.com.brunocarvalhs.billet_registration.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.billet_registration.databinding.FragmentBilletRegistrationFormBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.setupTextFieldDate
import br.com.brunocarvalhs.commons.utils.setupTextFieldMonth
import br.com.brunocarvalhs.commons.utils.setupTextFieldValue
import br.com.brunocarvalhs.data.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: BilletRegistrationFormViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
    }

    private val datePickerReferringMonth by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentBilletRegistrationFormBinding =
        FragmentBilletRegistrationFormBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is BilletRegistrationFormViewState.Error -> this.showError(it.error)
                BilletRegistrationFormViewState.Loading -> this.loading()
                BilletRegistrationFormViewState.Success -> cancelRegistration()
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.registration.setOnClickListener { viewModel.saveCost() }
        binding.cancel.setOnClickListener { cancelRegistration() }
        binding.prompt.setupTextFieldDate(this, datePicker, calendar)
        binding.value.editText?.setupTextFieldValue()
        binding.barcode.setEndIconOnClickListener { navigateToBarcodeScanner() }
        binding.referringMonth.setupTextFieldMonth(this, datePickerReferringMonth, calendar)
    }

    private fun navigateToBarcodeScanner() {
        val action = BilletRegistrationFormFragmentDirections
            .actionBilletRegistrationFormFragmentToBilletRegistrationBarcodeScannerFragment()
        findNavController().navigate(action)
    }

    private fun cancelRegistration() {
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
    }
}