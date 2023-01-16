package br.com.brunocarvalhs.payflow.features.billet_registration.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.FragmentBilletRegistrationFormBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    private val viewModel: BilletRegistrationFormViewModel by viewModels()

    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
        .build()


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
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
        binding.registration.setOnClickListener { createCost() }
        binding.cancel.setOnClickListener { cancelRegistration() }
        binding.barcode.editText?.setText(viewModel.barCode)
        binding.prompt.editText?.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }
        datePicker.addOnPositiveButtonClickListener {
            val date = SimpleDateFormat(FORMAT_DATE, Locale.getDefault()).format(it)
            binding.prompt.editText?.setText(date)
        }
    }

    override fun loading() {

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
            viewModel.saveCost(cost)
        }
    }

    private fun generateCost() = CostsModel(
        name = binding.name.editText?.text.toString(),
        prompt = binding.prompt.editText?.text.toString(),
        value = binding.value.editText?.text.toString().toDouble(),
        barCode = binding.barcode.editText?.text.toString()
    )

    private fun validateEditTexts(editTexts: List<EditText?>): Boolean {
        for (editText in editTexts) {
            if (editText == null || editText.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun cancelRegistration() {
        findNavController().popBackStack(R.id.costsFragment, true)
    }

    companion object {
        const val FORMAT_DATE = "dd/MM/yyyy"
    }
}