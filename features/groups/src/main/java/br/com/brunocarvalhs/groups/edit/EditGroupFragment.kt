package br.com.brunocarvalhs.groups.edit

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
import br.com.brunocarvalhs.groups.databinding.FragmentEditGroupBinding
import br.com.brunocarvalhs.groups.register.GroupRegisterFragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditGroupFragment : BaseFragment<FragmentEditGroupBinding>(),
    EditGroupMembersRecyclerViewAdapter.GroupRegisterMembersListeners {

    private val viewModel: EditGroupViewModel by viewModels()

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var analyticsService: AnalyticsService

    private val adapter by lazy {
        EditGroupMembersRecyclerViewAdapter(
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
    ): FragmentEditGroupBinding =
        FragmentEditGroupBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is EditGroupViewState.Error -> this.showError(it.message)
                EditGroupViewState.Loading -> this.loading()
                EditGroupViewState.Success -> this.navigateToCosts()
                EditGroupViewState.MemberSearchSuccess -> this.displayData()
            }
        }
    }

    private fun displayData() {
        renderListMembers()
    }

    private fun navigateToCosts() {
        val action = navigation.navigateToCosts()
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
        viewModel.fetchData()
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
            viewModel.update()
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
}