package br.com.brunocarvalhs.groups.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.groups.R
import br.com.brunocarvalhs.groups.databinding.FragmentGroupRegisterBinding
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroupRegisterFragment : BaseFragment<FragmentGroupRegisterBinding>(),
    GroupRegisterMembersRecyclerViewAdapter.GroupRegisterMembersListeners {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: GroupRegisterViewModel by viewModels()


    @Inject
    lateinit var analyticsService: AnalyticsService

    private val adapter by lazy {
        GroupRegisterMembersRecyclerViewAdapter(
            requireContext(),
            viewModel,
            this
        )
    }

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
        setupList()
        binding.name.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) binding.name.error = null
        }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
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
        adapter.submitList(viewModel.members)
    }

    private fun setupButtonRegistration() {
        binding.registration.setOnClickListener { save() }
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

    override fun onRemoveUser(member: UserEntities) {
        viewModel.removeMember(member) {
            adapter.removeUser(member)
        }
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