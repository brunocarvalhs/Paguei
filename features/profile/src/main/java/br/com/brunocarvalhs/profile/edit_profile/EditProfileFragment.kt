package br.com.brunocarvalhs.profile.edit_profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.profile.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel: EditProfileViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentEditProfileBinding =
        FragmentEditProfileBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is EditProfileViewState.Error -> this.showError(it.message)
                EditProfileViewState.Loading -> this.loading()
                EditProfileViewState.Success -> this.navigationToProfile()
            }
        }
    }

    private fun navigationToProfile() {
        val action = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.update.setOnClickListener { viewModel.update() }
        setupSalary()
        setupName()
    }

    override fun loading() {

    }

    private fun setupName() {
        binding.name.setEndIconOnClickListener {
            if (binding.name.editText?.isEnabled == false) {
                binding.name.editText?.defineEnabled()
                binding.name.isEndIconVisible = false
                defineUpdateButton()
            }
        }
    }

    private fun setupSalary() {
        binding.salary.setEndIconOnClickListener {
            if (binding.salary.editText?.isEnabled == false) {
                binding.salary.editText?.defineEnabled()
                binding.salary.isEndIconVisible = false
                defineUpdateButton()
            }
        }
        setupTextFieldValue()
    }

    private fun View.defineEnabled() {
        this.isEnabled = !this.isEnabled
    }

    private fun setupTextFieldValue() {
        binding.salary.editText?.let { valueEditText ->
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
        binding.update.visibility = View.VISIBLE
    }

    private fun String.moneyToDouble() =
        this.replace("\\D".toRegex(), "").toDouble()
}