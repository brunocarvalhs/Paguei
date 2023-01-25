package br.com.brunocarvalhs.paguei.features.groups.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.model.GroupsModel
import br.com.brunocarvalhs.paguei.databinding.FragmentHomesRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomesRegisterFragment : BaseFragment<FragmentHomesRegisterBinding>() {

    private val viewModel: HomesRegisterViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomesRegisterBinding =
        FragmentHomesRegisterBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is HomesRegisterViewState.Error -> this.showError(it.error)
                HomesRegisterViewState.Loading -> this.loading()
                HomesRegisterViewState.Success -> this.navigateToCosts()
            }
        }
    }

    private fun navigateToCosts() {
        findNavController().popBackStack()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.registration.setOnClickListener { createHomes() }
    }

    private fun createHomes() {
        val fieldsOfForm = listOf(
            binding.name.editText,
            binding.members.editText,
        )
        if (validateEditTexts(fieldsOfForm)) {
            val group = generateHomes()
            viewModel.save(group)
        }
    }

    private fun validateEditTexts(editTexts: List<EditText?>): Boolean {
        for (editText in editTexts) {
            if (editText == null || editText.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    override fun loading() {

    }

    private fun generateHomes() = GroupsModel(
        name = binding.name.editText?.text.toString(),
        members = viewModel.members
    )
}