package br.com.brunocarvalhs.profile.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.commons.utils.setupEditTextField
import br.com.brunocarvalhs.commons.utils.setupTextFieldValue
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.profile.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    private val viewModel: EditProfileViewModel by viewModels()


    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            EditProfileFragment::class.simpleName.orEmpty(),
            EditProfileFragment::class
        )
    }

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
        binding.name.setupEditTextField(binding.update)
    }

    private fun setupSalary() {
        binding.salary.setupEditTextField(binding.update)
        binding.salary.editText?.setupTextFieldValue()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}