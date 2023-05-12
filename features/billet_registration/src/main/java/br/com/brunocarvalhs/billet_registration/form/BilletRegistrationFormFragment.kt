package br.com.brunocarvalhs.billet_registration.form

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.billet_registration.databinding.FragmentBilletRegistrationFormBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.utils.FORMAT_MONTH
import br.com.brunocarvalhs.data.utils.PROMPT_FORMAT
import br.com.brunocarvalhs.data.utils.moneyToDouble
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: BilletRegistrationFormViewModel by viewModels()

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
    }

    private val datePickerReferringMonth by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
    }

    private val calendar by lazy { Calendar.getInstance() }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentBilletRegistrationFormBinding =
        FragmentBilletRegistrationFormBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is BilletRegistrationFormViewState.Error -> this.showError(it.error)
                BilletRegistrationFormViewState.Loading -> this.loading()
                BilletRegistrationFormViewState.Success -> this.navigateToHome()
            }
        }
    }

    private fun navigateToHome() {
        cancelRegistration()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupButtonRegister()
        setupButtonCancel()
        setupTextFieldPrompt()
        setupTextFieldValue()
        setupTextFieldBarcode()
        setupTextFieldReferringMonth()
    }

    private fun setupButtonRegister() {
        binding.registration.setOnClickListener { viewModel.saveCost() }
    }

    private fun setupButtonCancel() {
        binding.cancel.setOnClickListener { cancelRegistration() }
    }

    private fun setupTextFieldBarcode() {
        binding.barcode.setEndIconOnClickListener {
            navigateToBarcodeScanner()
        }
    }

    private fun navigateToBarcodeScanner() {
        val action = BilletRegistrationFormFragmentDirections
            .actionBilletRegistrationFormFragmentToBilletRegistrationBarcodeScannerFragment()
        findNavController().navigate(action)
    }

    override fun loading() {

    }

    private fun cancelRegistration() {
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
    }

    private fun setupTextFieldPrompt() {
        initDateConfig(binding.prompt.editText)
        binding.prompt.setEndIconOnClickListener { showDateAlert(datePicker) }
        eventSetDateTextField(binding.prompt.editText, datePicker)
    }

    private fun setupTextFieldReferringMonth() {
        initDateConfig(binding.referringMonth.editText)
        binding.referringMonth.setEndIconOnClickListener { showDateAlert(datePickerReferringMonth) }
        eventSetDateTextField(binding.referringMonth.editText, datePickerReferringMonth)
    }

    private fun showDateAlert(datePicker: MaterialDatePicker<Long>) {
        datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
    }

    private fun eventSetDateTextField(editText: EditText?, datePicker: MaterialDatePicker<Long>) {
        datePicker.addOnPositiveButtonClickListener {
            calendar.time = Date(it)
            calendar.add(Calendar.DATE, 1)
            val date = SimpleDateFormat(FORMAT_MONTH, Locale.getDefault()).format(calendar.time)
            editText?.setText(date)
        }
    }

    private fun initDateConfig(editText: EditText?) {
        editText?.let {
            val listener = MaskedTextChangedListener(PROMPT_FORMAT, it)
            it.addTextChangedListener(listener)
            it.onFocusChangeListener = listener
        }
    }

    private fun setupTextFieldValue() {
        binding.value.editText?.let { valueEditText ->
            val currencyFormat = DecimalFormat.getCurrencyInstance()
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        if (s.isNotEmpty()) {
                            val parsed = s.toString().moneyToDouble() / 100
                            val formatted = currencyFormat.format(parsed)
                            valueEditText.removeTextChangedListener(this)
                            valueEditText.setText(formatted)
                            valueEditText.setSelection(formatted.length)
                            valueEditText.addTextChangedListener(this)
                        }
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    Unit
            }
            valueEditText.addTextChangedListener(textWatcher)
        }
    }
}