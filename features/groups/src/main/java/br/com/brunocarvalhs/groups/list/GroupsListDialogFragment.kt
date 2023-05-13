package br.com.brunocarvalhs.groups.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.groups.databinding.DialogGroupsListBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GroupsListDialogFragment : BaseBottomSheetDialogFragment<DialogGroupsListBinding>(),
    GroupsRecyclerViewAdapter.GroupsClickListener {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: GroupsListViewModel by viewModels()
    private val adapter by lazy { GroupsRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogGroupsListBinding =
        DialogGroupsListBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        this.setupButtonCreate()
        this.setupList()
    }

    private fun setupButtonCreate() =
        binding.create.setOnClickListener { navigateToRegisterGroups() }

    private fun setupOptionMyProfile(user: UserEntities?) {
        user?.let {
            Glide.with(requireActivity()).load(it.photoUrl).centerCrop().into(binding.avatar)
            binding.nameUser.text = it.name
            binding.avatarGroup.setOnClickListener { selected(null) }
        }
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                GroupsListViewState.Loading -> this.loading()
                is GroupsListViewState.Success -> this.displayData(it.list)
                is GroupsListViewState.Error -> this.showError(it.error)
                is GroupsListViewState.SuccessUser -> this.setupOptionMyProfile(it.user)
            }
        }
    }

    private fun displayData(list: List<GroupEntities>) = adapter.submitList(list)

    override fun argumentsView(arguments: Bundle) {

    }

    override fun onClick(group: GroupEntities?) = selected(group)

    private fun selected(group: GroupEntities?) {
        viewModel.selected(group)
        val action = navigation.navigateToCostsRegister()
        findNavController().navigate(action)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun navigateToRegisterGroups() {
        val action = GroupsListDialogFragmentDirections
            .actionHomesListDialogFragmentToHomesRegisterFragment()
        findNavController().navigate(action)
    }
}