package br.com.brunocarvalhs.groups.edit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.groups.databinding.FragmentGroupRegisterBinding
import br.com.brunocarvalhs.groups.edit.state.EditGroupViewState
import br.com.brunocarvalhs.groups.edit.viewmodel.EditGroupViewModel

class EditGroupFragment : BaseFragment<FragmentGroupRegisterBinding>() {

    private val viewModel: EditGroupViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentGroupRegisterBinding =
        FragmentGroupRegisterBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is EditGroupViewState.Error -> this.showError(it.message)
                EditGroupViewState.Loading -> this.loading()
                EditGroupViewState.Success -> this.displayData()
            }
        }
    }

    private fun displayData() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {

    }

    override fun loading() {

    }
}