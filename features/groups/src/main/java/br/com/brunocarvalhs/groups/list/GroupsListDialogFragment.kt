package br.com.brunocarvalhs.groups.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.groups.databinding.DialogGroupsListBinding
import br.com.brunocarvalhs.paguei.features.groups.list.GroupsListViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupsListDialogFragment : BaseBottomSheetDialogFragment<DialogGroupsListBinding>(),
    GroupsRecyclerViewAdapter.GroupsClickListener {

    private val viewModel: GroupsListViewModel by viewModels()
    private val adapter by lazy { GroupsRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogGroupsListBinding =
        DialogGroupsListBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        this.setupOptionMyProfile()
        this.setupButtonCreate()
        this.setupList()
    }

    private fun setupButtonCreate() {
        binding.create.setOnClickListener { navigateToRegisterHomes() }
    }

    private fun setupOptionMyProfile() {
        viewModel.user?.let {
            Glide.with(requireActivity()).load(it.photoUrl).centerCrop().into(binding.avatar)
            binding.nameUser.text = it.name
            binding.avatarGroup.setOnClickListener { viewModel.selected(home = null) }
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
            }
        }
    }

    private fun displayData(list: List<GroupEntities>) {
        adapter.submitList(list)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }

    override fun onClick(group: GroupEntities?) {
        viewModel.selected(group)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun navigateToRegisterHomes() {
        val action = GroupsListDialogFragmentDirections
            .actionHomesListDialogFragmentToHomesRegisterFragment()
        findNavController().navigate(action)
    }
}