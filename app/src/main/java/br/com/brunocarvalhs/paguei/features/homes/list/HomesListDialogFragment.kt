package br.com.brunocarvalhs.paguei.features.homes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.paguei.databinding.DialogHomesListBinding
import br.com.brunocarvalhs.paguei.domain.entities.HomesEntities
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomesListDialogFragment : BaseBottomSheetDialogFragment<DialogHomesListBinding>(),
    HomesRecyclerViewAdapter.HomesClickListener {

    private val viewModel: HomesListViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogHomesListBinding =
        DialogHomesListBinding.inflate(inflater, container, attachToParent)

    override fun initView() {
        viewModel.user?.let {
            Glide.with(this).load(it.photoUrl).centerCrop().into(binding.avatar)
            binding.nameUser.text = it.name
            binding.avatarGroup.setOnClickListener { viewModel.selected(home = null) }
        }
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.create.setOnClickListener { navigateToRegisterHomes() }
    }

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                HomesListViewState.Loading -> this.loading()
                is HomesListViewState.Success -> this.displayData(it.list)
                is HomesListViewState.Error -> this.showError(it.error)
            }
        }
    }

    private fun displayData(list: List<HomesEntities>) {
        binding.list.adapter = HomesRecyclerViewAdapter(list, this)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }

    override fun onClick(home: HomesEntities?) {
        viewModel.selected(home)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun navigateToRegisterHomes() {
        val action =
            HomesListDialogFragmentDirections.actionHomesListDialogFragmentToHomesRegisterFragment()
        findNavController().navigate(action)
    }
}