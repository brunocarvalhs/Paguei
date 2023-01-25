package br.com.brunocarvalhs.paguei.features.billet_registration.form

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.paguei.databinding.FragmentBilletRegistrationFormBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    private val viewModel: BilletRegistrationFormViewModel by viewModels()

    private val datePicker =
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

    private val calendar = Calendar.getInstance()

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
        binding.registration.setOnClickListener { viewModel.saveCost() }
        binding.cancel.setOnClickListener { cancelRegistration() }
        setupTextFieldDate()
        setupTextFieldValue()
    }

    override fun loading() {

    }

    private fun cancelRegistration() {
        val action = BilletRegistrationFormFragmentDirections
            .actionBilletRegistrationFormFragmentToCostsFragment()
        findNavController().navigate(action)
    }

    private fun setupTextFieldDate() {
        binding.prompt.editText?.let {
            val listener = MaskedTextChangedListener(PROMPT_FORMAT, it)
            it.addTextChangedListener(listener)
            it.onFocusChangeListener = listener
        }
        binding.prompt.setEndIconOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }
        datePicker.addOnPositiveButtonClickListener {
            calendar.time = Date(it)
            calendar.add(Calendar.DATE, 1)
            val date = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(calendar.time)
            binding.prompt.editText?.setText(date)
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
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
            valueEditText.addTextChangedListener(textWatcher)
        }
    }

    private fun String.moneyToDouble() =
        this.replace(REGEX_TEXT.toRegex(), "").toDouble()

    companion object {
        const val FORMAT_DATE = "dd/MM/yyyy"
        const val PROMPT_FORMAT = "[00]{/}[00]{/}[0000]"
        const val REGEX_TEXT = "[^\\d]"
    }
}