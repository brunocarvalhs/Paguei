package br.com.brunocarvalhs.costs.reader_cost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.setupEditTextField
import br.com.brunocarvalhs.commons.utils.setupEditTextFieldDate
import br.com.brunocarvalhs.commons.utils.setupEditTextFieldMonth
import br.com.brunocarvalhs.commons.utils.setupEditTextFieldValue
import br.com.brunocarvalhs.commons.utils.textCopyThenPost
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.costs.databinding.FragmentCostReaderBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class CostReaderFragment : BaseFragment<FragmentCostReaderBinding>() {

    private val viewModel: CostReaderViewModel by viewModels()

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
    }

    private val dateMonthPicker by lazy {
        MaterialDatePicker.Builder.datePicker().setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()
    }

    private val calendar by lazy { Calendar.getInstance() }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCostReaderBinding =
        FragmentCostReaderBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CostReaderViewState.Error -> this.showError(it.error)
                CostReaderViewState.Loading -> this.loading()
                is CostReaderViewState.Success -> findNavController().popBackStack()
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.name.setupEditTextField(binding.update)
        binding.prompt.setupEditTextFieldDate(
            this, binding.update, R.drawable.ic_baseline_today_24, datePicker, calendar
        )
        binding.value.setupEditTextFieldValue(binding.update)
        binding.barcode.setEndIconOnClickListener {
            viewModel.cost.barCode?.let {
                requireContext().textCopyThenPost(it, R.string.copy_success)
            }
        }
        binding.referringMonth.setupEditTextFieldMonth(
            this,
            binding.update,
            R.drawable.ic_baseline_calendar_month_24,
            dateMonthPicker,
            calendar
        )
        binding.update.setOnClickListener { viewModel.updateCost() }
    }
}