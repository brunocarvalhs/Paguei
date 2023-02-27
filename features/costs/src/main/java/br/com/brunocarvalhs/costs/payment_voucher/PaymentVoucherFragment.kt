package br.com.brunocarvalhs.costs.payment_voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.costs.databinding.FragmentPaymentVoucherBinding
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.data.utils.FORMAT_DATE
import br.com.brunocarvalhs.data.utils.PROMPT_FORMAT
import br.com.brunocarvalhs.paguei.features.costs.payment_voucher.PaymentVoucherViewState
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.redmadrobot.inputmask.MaskedTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class PaymentVoucherFragment : BaseFragment<FragmentPaymentVoucherBinding>() {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: PaymentVoucherViewModel by viewModels()

    private val calendar by lazy { Calendar.getInstance() }

    private val constraints = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointBackward.before(calendar.timeInMillis))
        .build()

    private val datePickerPayment by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setCalendarConstraints(constraints)
            .build()
    }

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
        setupTextFieldDatePayment()
    }

    override fun loading() {

    }

    private fun setupTextFieldDatePayment() {
        initDateConfig(binding.datePayment.editText)
        binding.datePayment.setEndIconOnClickListener { showDateAlert(datePickerPayment) }
        eventSetDateTextField(binding.datePayment.editText, datePickerPayment)
    }

    private fun initDateConfig(editText: EditText?) {
        editText?.let {
            val listener = MaskedTextChangedListener(PROMPT_FORMAT, it)
            it.addTextChangedListener(listener)
            it.onFocusChangeListener = listener
        }
    }

    private fun eventSetDateTextField(editText: EditText?, datePicker: MaterialDatePicker<Long>) {
        datePicker.addOnPositiveButtonClickListener {
            calendar.time = Date(it)
            calendar.add(Calendar.DATE, 1)
            val date = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(calendar.time)
            editText?.setText(date)
        }
    }

    private fun showDateAlert(datePicker: MaterialDatePicker<Long>) {
        datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
    }
}