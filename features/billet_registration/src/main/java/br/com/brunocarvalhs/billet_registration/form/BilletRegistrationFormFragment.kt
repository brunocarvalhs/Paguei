package br.com.brunocarvalhs.billet_registration.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.billet_registration.R
import br.com.brunocarvalhs.billet_registration.databinding.FragmentBilletRegistrationFormBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.setupTextFieldDate
import br.com.brunocarvalhs.commons.utils.setupTextFieldMonth
import br.com.brunocarvalhs.commons.utils.setupTextFieldValue
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AdsService
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.google.android.material.datepicker.MaterialDatePicker
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var adsService: AdsService

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

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            BilletRegistrationFormFragment::class.simpleName.orEmpty(),
            BilletRegistrationFormFragment::class
        )
        adsService.initFullBanner(getString(R.string.costs_register_banner))
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
        binding.registration.setOnClickListener { saveCost() }
        binding.cancel.setOnClickListener { cancelRegistration() }
        binding.prompt.setupTextFieldDate(this, datePicker, calendar)
        binding.value.editText?.setupTextFieldValue()
        binding.barcode.setEndIconOnClickListener { startBarcodeScanner() }
        binding.referringMonth.setupTextFieldMonth(this, datePickerReferringMonth, calendar)

        binding.name.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.name.error = null
            }
        }

        binding.prompt.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.prompt.error = null
            }
        }

        binding.value.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.value.error = null
            }
        }

        binding.barcode.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.barcode.error = null
            }
        }
    }

    private fun saveCost() {
        if (validateFields()) {
            viewModel.saveCost()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        binding.name.error = null
        binding.prompt.error = null
        binding.value.error = null
        binding.barcode.error = null

        if (binding.name.editText?.text.isNullOrBlank()) {
            binding.name.error = getString(
                R.string.error_empty_field,
                getString(R.string.billet_registration_textfield_name_hilt)
            )
            isValid = false
        }

        if (binding.prompt.editText?.text.isNullOrBlank()) {
            binding.prompt.error = getString(
                R.string.error_empty_field,
                getString(R.string.billet_registration_textfield_prompt_hilt)
            )
            isValid = false
        }

        if (binding.value.editText?.text.isNullOrBlank()) {
            binding.value.error = getString(
                R.string.error_empty_field,
                getString(R.string.billet_registration_textfield_value_hilt)
            )
            isValid = false
        }

        if (binding.barcode.editText?.text.isNullOrBlank()) {
            binding.barcode.error = getString(
                R.string.error_empty_field,
                getString(R.string.billet_registration_textfield_barcode_hilt)
            )
            isValid = false
        }

        return isValid
    }

    private fun cancelRegistration() {
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
        adsService.fullBanner(requireActivity())
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            val barcode = result.contents
            viewModel.barCode.set(barcode)
        }
    }

    private fun startBarcodeScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ITF, ScanOptions.QR_CODE)
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }
}