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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BilletRegistrationFormFragment : BaseFragment<FragmentBilletRegistrationFormBinding>() {

    private val viewModel: BilletRegistrationFormViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentBilletRegistrationFormBinding.inflate(inflater, container, false)

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
}