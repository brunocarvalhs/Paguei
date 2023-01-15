package br.com.brunocarvalhs.payflow.features.homes.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.payflow.databinding.DialogHomesListBinding
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
            Glide.with(this).load(it.photoUrl).centerCrop().into(binding.avatarUser)
            binding.nameUser.text = it.name
            binding.avatarGroup.setOnClickListener { findNavController().popBackStack() }
        }
        binding.list.layoutManager = LinearLayoutManager(context)
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

    private fun displayData(list: List<String>) {
        binding.list.adapter = HomesRecyclerViewAdapter(list, this)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }

    override fun onClick(home: String) {

    }

    override fun onLongClick(home: String): Boolean {
        return true
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}