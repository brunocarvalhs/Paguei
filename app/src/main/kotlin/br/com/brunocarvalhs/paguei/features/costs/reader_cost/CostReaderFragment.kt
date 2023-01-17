package br.com.brunocarvalhs.paguei.features.costs.reader_cost

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.FragmentCostReaderBinding
import br.com.brunocarvalhs.paguei.features.billet_registration.form.BilletRegistrationFormFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CostReaderFragment : BaseFragment<FragmentCostReaderBinding>() {

    private val viewModel: CostReaderViewModel by viewModels()

    private val datePicker =
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

    private val calendar = Calendar.getInstance()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCostReaderBinding =
        FragmentCostReaderBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CostReaderViewState.Error -> this.showError(it.error)
                CostReaderViewState.Loading -> this.loading()
                is CostReaderViewState.Success -> this.navigateToCosts()
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
        setupName()
        setupPrompt()
        setupValue()
        setupBarcode()
        binding.registration.setOnClickListener { createCost() }
    }

    private fun setupBarcode() {
        binding.barcode.editText?.setText(viewModel.cost.barCode)
        binding.barcode.setEndIconOnClickListener {
            viewModel.cost.barCode?.let { textCopyThenPost(it) }
        }
    }

    private fun setupName() {
        binding.name.editText?.setText(viewModel.cost.name)
        binding.name.setEndIconOnClickListener {
            if (binding.name.editText?.isEnabled == false) {
                binding.name.editText?.defineEnabled()
                binding.name.isEndIconVisible = false
                defineUpdateButton()
            }
        }
    }

    private fun setupPrompt() {
        binding.prompt.editText?.setText(viewModel.cost.prompt)
        binding.prompt.setEndIconOnClickListener {
            if (binding.prompt.editText?.isEnabled == false) {
                binding.prompt.editText?.defineEnabled()
                binding.prompt.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
                defineUpdateButton()
            } else {
                datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
            }
        }
        binding.prompt.editText?.let {
            val listener =
                MaskedTextChangedListener(BilletRegistrationFormFragment.PROMPT_FORMAT, it)
            it.addTextChangedListener(listener)
            it.onFocusChangeListener = listener
        }
        datePicker.addOnPositiveButtonClickListener {
            calendar.time = Date(it)
            calendar.add(Calendar.DATE, 1)
            val date = SimpleDateFormat(
                BilletRegistrationFormFragment.FORMAT_DATE, Locale.getDefault()
            ).format(calendar.time)
            binding.prompt.editText?.setText(date)
        }
    }

    private fun setupValue() {
        binding.value.editText?.setText(viewModel.cost.formatValue())
        binding.value.setEndIconOnClickListener {
            if (binding.value.editText?.isEnabled == false) {
                binding.value.editText?.defineEnabled()
                binding.value.isEndIconVisible = false
                defineUpdateButton()
            }
        }
        setupTextFieldValue()
    }

    override fun loading() {

    }

    private fun textCopyThenPost(textCopied: String) {
        val clipboardManager =
            requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
        Toast.makeText(requireContext(), "Código copiado com sucesso.", Toast.LENGTH_SHORT).show()
    }

    private fun View.defineEnabled() {
        this.isEnabled = !this.isEnabled
    }

    private fun createCost() {
        val fieldsOfForm = listOf(
            binding.name.editText,
            binding.prompt.editText,
            binding.value.editText,
            binding.barcode.editText
        )
        if (validateEditTexts(fieldsOfForm)) {
            val cost = generateCost()
            viewModel.updateCost(cost)
        }
    }

    private fun generateCost() = viewModel.cost.copy(
        name = binding.name.editText?.text.toString(),
        prompt = binding.prompt.editText?.text.toString(),
        value = binding.value.editText?.text.toString().moneyToDouble(),
        barCode = binding.barcode.editText?.text.toString()
    )

    private fun String.moneyToDouble() =
        this.replace(BilletRegistrationFormFragment.REGEX_TEXT.toRegex(), "").toDouble()

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
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
            valueEditText.addTextChangedListener(textWatcher)
        }
    }

    private fun defineUpdateButton() {
        binding.registration.visibility = View.VISIBLE
    }

    private fun validateEditTexts(editTexts: List<EditText?>): Boolean {
        for (editText in editTexts) {
            if (editText == null || editText.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }
}