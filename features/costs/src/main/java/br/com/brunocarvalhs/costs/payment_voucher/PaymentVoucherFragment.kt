package br.com.brunocarvalhs.costs.payment_voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.setupTextFieldDate
import br.com.brunocarvalhs.costs.databinding.FragmentPaymentVoucherBinding
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class PaymentVoucherFragment : BaseFragment<FragmentPaymentVoucherBinding>() {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: PaymentVoucherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }

    private val constraints = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointBackward.before(calendar.timeInMillis)).build()

    private val datePickerPayment by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setCalendarConstraints(constraints).build()
    }

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            PaymentVoucherFragment::class.simpleName.orEmpty(),
            PaymentVoucherFragment::class
        )
        analyticsService.trackEvent(
            AnalyticsService.Events.SELECT_ITEM,
            mapOf(Pair("cost_id", viewModel.cost.id)),
            PaymentVoucherFragment::class
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
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
        val action = navigation.navigateToCosts()
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.registration.setOnClickListener {
            viewModel.savePaymentVoucher()

            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "registration")),
                PaymentVoucherFragment::class
            )
        }
        binding.datePayment.setupTextFieldDate(this, datePickerPayment, calendar)
    }
}