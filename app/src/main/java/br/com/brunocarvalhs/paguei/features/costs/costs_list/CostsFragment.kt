package br.com.brunocarvalhs.paguei.features.costs.costs_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.FragmentCostsListBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsFragment : BaseFragment<FragmentCostsListBinding>(),
    CostsRecyclerViewAdapter.CostClickListener {

    private val viewModel: CostsViewModel by viewModels()
    private val adapter by lazy { CostsRecyclerViewAdapter(requireContext(), this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentCostsListBinding =
        FragmentCostsListBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                CostsViewState.Loading -> this.loading()
                is CostsViewState.Success -> this.displayData(it.list)
                is CostsViewState.Error -> this.showError(it.message)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun displayData(list: List<CostsEntities>) {
        defineTotalCosts(list.size)
        adapter.submitList(list)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        this.setupHeader()
        this.setupNavigation()
        this.setupList()
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun loading() {

    }

    private fun defineTotalCosts(total: Int = 0) {
        binding.textTotalCosts.text =
            requireActivity().getString(R.string.costs_total_text, total.toString())
    }

    private fun setupHeader() {
        viewModel.user?.let {
            it.photoUrl?.let { photoUrl ->
                Glide.with(this).load(photoUrl).centerCrop().into(binding.avatar)
            } ?: run {
                binding.avatar.visibility = View.GONE
                binding.avatarText.visibility = View.VISIBLE
                binding.avatarText.text = it.initialsName()
            }
            binding.name.text =
                requireActivity().getString(R.string.home_title_header, it.fistName())
            binding.avatar.setOnClickListener { navigateToProfile() }
            binding.name.setOnClickListener { navigateToProfile() }
        }
    }

    private fun setupNavigation() {
        binding.add.setOnClickListener { navigateToAddCosts() }
        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.extractFragment -> navigateToExtracts()
                else -> false
            }
        }
    }

    private fun navigateToProfile() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://paguei.app/profile_fragment".toUri())
            .build()

        findNavController().navigate(request)
    }

    private fun navigateToExtracts(): Boolean {
        val action = CostsFragmentDirections.actionHomeFragmentToExtractFragment()
        findNavController().navigate(action)
        return true
    }

    private fun navigateToReaderCost(cost: CostsEntities) {
        val action = CostsFragmentDirections.actionCostsFragmentToCostReaderFragment(cost)
        findNavController().navigate(action)
    }


    private fun navigateToAddCosts() {
        val action = CostsFragmentDirections
            .actionCostsFragmentToBilletRegistrationBarcodeScannerFragment()
        findNavController().navigate(action)
    }

    override fun onClick(cost: CostsEntities) = navigateToReaderCost(cost)

    override fun onLongClick(cost: CostsEntities): Boolean {
        val action =
            CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(cost)
        findNavController().navigate(action)

        return true
    }
}