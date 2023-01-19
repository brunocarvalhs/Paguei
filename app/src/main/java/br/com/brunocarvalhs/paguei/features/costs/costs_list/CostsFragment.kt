package br.com.brunocarvalhs.paguei.features.costs.costs_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.FragmentCostsListBinding
import br.com.brunocarvalhs.domain.entities.CostsEntities
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsFragment : BaseFragment<FragmentCostsListBinding>(),
    CostsRecyclerViewAdapter.CostClickListener {

    private val viewModel: CostsViewModel by viewModels()

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

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun displayData(list: List<CostsEntities>) {
        defineTotalCosts(list.size)
        binding.list.adapter = CostsRecyclerViewAdapter(requireContext(), list, this)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    @SuppressLint("StringFormatMatches")
    override fun initView() {
        this.visibilityToolbar(true)
        binding.list.layoutManager = LinearLayoutManager(context)
        this.setupHeader()
        this.setupNavigation()
    }

    override fun loading() {
        defineTotalCosts()
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
//                R.id.homesListDialogFragment -> navigateToHomes()
                else -> false
            }
        }
    }

    private fun navigateToProfile() {
        val action = CostsFragmentDirections.actionCostsFragmentToProfileFragment()
        findNavController().navigate(action)
    }

//    private fun navigateToHomes(): Boolean {
//        val action = CostsFragmentDirections.actionCostsFragmentToHomesListDialogFragment()
//        findNavController().navigate(action)
//        return true
//    }

    private fun navigateToExtracts(): Boolean {
        val action = CostsFragmentDirections.actionHomeFragmentToExtractFragment()
        findNavController().navigate(action)
        return true
    }

    private fun navigateToReaderCost(cost: CostsEntities) {
        val action =
            CostsFragmentDirections.actionCostsFragmentToCostReaderFragment(cost as CostsModel)
        findNavController().navigate(action)
    }


    private fun navigateToAddCosts() {
        val action =
            CostsFragmentDirections.actionCostsFragmentToBilletRegistrationBarcodeScannerFragment()
        findNavController().navigate(action)
    }

    override fun onClick(cost: CostsEntities) = navigateToReaderCost(cost)

    override fun onLongClick(cost: CostsEntities): Boolean {
        val action =
            CostsFragmentDirections.actionHomeFragmentToItemListDialogFragment(cost as CostsModel)
        findNavController().navigate(action)

        return true
    }
}