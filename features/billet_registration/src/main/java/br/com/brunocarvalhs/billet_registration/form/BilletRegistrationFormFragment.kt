package br.com.brunocarvalhs.billet_registration.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
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
        analyticsService.trackEvent(
            AnalyticsService.Events.SCREEN_VIEWED,
            mapOf(Pair("screen_name", "BilletRegistrationForm")),
            BilletRegistrationFormFragment::class
        )
        adsService.initFullBanner(getString(R.string.costs_register_banner))
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
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
                is BilletRegistrationFormViewState.ListName -> this.setupAutoCompleteName(it.names)
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.registration.setOnClickListener {
            saveCost()
            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "registration")),
                BilletRegistrationFormFragment::class
            )
        }
        binding.cancel.setOnClickListener {
            cancelRegistration()
            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "cancel")),
                BilletRegistrationFormFragment::class
            )
        }
        binding.prompt.setupTextFieldDate(this, datePicker, calendar)
        binding.value.editText?.setupTextFieldValue()
        binding.barcode.setEndIconOnClickListener {
            startBarcodeScanner()
            analyticsService.trackEvent(
                AnalyticsService.Events.ICON_CLICKED,
                mapOf(Pair("icon_name", "barcode")),
                BilletRegistrationFormFragment::class
            )
        }
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
        setupTypes()
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
        val action = navigation.navigateToCosts()
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

    fun startBarcodeScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ITF, ScanOptions.QR_CODE)
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        barcodeLauncher.launch(options)
    }

    private fun setupAutoCompleteName(names: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, names
        )
        binding.nameAutoComplete.setAdapter(adapter)
    }

    private fun setupTypes() {
        val list = TypeCost.values().map { createType(it) }
        list.forEach { binding.types.addView(it) }
        binding.types.check(TypeCost.FIX.ordinal)
        binding.types.setOnCheckedChangeListener { _, checkedId ->
            viewModel.type.set(TypeCost.ordinalOf(checkedId).name)
        }
    }

    private fun createType(type: TypeCost): RadioButton {
        val button = RadioButton(requireContext())
        button.id = type.ordinal
        button.text = getString(type.value)
        return button
    }
}