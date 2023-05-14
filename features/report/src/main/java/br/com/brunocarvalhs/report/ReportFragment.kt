package br.com.brunocarvalhs.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.report.databinding.FragmentReportBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>() {

    private val viewModel: ReportViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ReportFragmentStateAdapter

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentReportBinding = FragmentReportBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ReportViewState.Error -> this.showError(it.message)
                ReportViewState.Loading -> this.loading()
                ReportViewState.Success -> this.displayData()
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    private fun displayData() {
        setupFilters()
    }

    override fun initView() {
        visibilityToolbar(true)
        viewPager = binding.myPager
        adapter = ReportFragmentStateAdapter(this)
        setupFilters()
    }

    private fun setupFilters() {
        val list = viewModel.defineFilters()

        viewPager.adapter = adapter

        list.forEach { item ->
            adapter.addFragment(ReportMonthFragment.newInstance(item.value))
            val index = list.keys.indexOf(item.key)
            viewPager.setCurrentItem(index, false)
        }

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = list.keys.toMutableList()[position]
        }.attach()
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}