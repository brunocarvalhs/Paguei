package br.com.brunocarvalhs.groups.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.groups.R
import br.com.brunocarvalhs.groups.databinding.FragmentGroupRegisterBinding
import com.google.android.material.chip.Chip
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroupRegisterFragment : BaseFragment<FragmentGroupRegisterBinding>() {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: GroupRegisterViewModel by viewModels()


    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            GroupRegisterFragment::class.simpleName.orEmpty(), GroupRegisterFragment::class
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentGroupRegisterBinding =
        FragmentGroupRegisterBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is GroupRegisterViewState.Error -> this.showError(it.error)
                GroupRegisterViewState.Loading -> this.loading()
                GroupRegisterViewState.Success -> this.navigateToCosts()
                GroupRegisterViewState.MemberSearchSuccess -> this.displayData()
            }
        }
    }

    private fun displayData() {
        binding.membersContainer.removeAllViews()
        renderListMembers()
    }

    private fun navigateToCosts() {
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupButtonRegistration()
        setupButtonCancel()
        setupMembers()

        binding.name.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.name.error = null
        }

        binding.members.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.members.error = null
        }
    }

    private fun setupButtonCancel() {
        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupMembers() {
        binding.members.setEndIconOnClickListener { startBarcodeScanner() }
        binding.members.editText?.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.registerMember()
                textView.text = String()
                return@setOnEditorActionListener true
            }
            false
        }
        renderListMembers()
    }

    private fun renderListMembers() {
        viewModel.members.forEach { createMembersToContainer(it) }
    }

    private fun setupButtonRegistration() {
        binding.registration.setOnClickListener { save() }
    }

    private fun createMembersToContainer(member: UserEntities) {
        val chip = Chip(requireContext())
        chip.text = member.name
        chip.closeIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_add_24)
        chip.isCloseIconVisible = viewModel.isIconCloseVisibility(member)
        chip.setOnCloseIconClickListener { view ->
            viewModel.removeMember(member) {
                binding.membersContainer.removeView(view)
            }
        }
        chip.setPadding(32)
        binding.membersContainer.addView(chip)
    }

    private fun save() {
        if (validateFields()) {
            viewModel.save()
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true

        binding.name.error = null
        binding.members.error = null

        if (binding.name.editText?.text.isNullOrBlank()) {
            binding.name.error = getString(
                R.string.error_empty_field, getString(R.string.textfield_name_text)
            )
            isValid = false
        }

        if (binding.members.editText?.text.isNullOrBlank()) {
            binding.members.error = getString(
                R.string.error_empty_field, getString(R.string.textfield_members_text_edit)
            )
            isValid = false
        }

        return isValid
    }

    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            val qrCode = result.contents.reversed()
            viewModel.registerMember(qrCode)
        }
    }

    private fun startBarcodeScanner() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
}