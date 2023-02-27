package br.com.brunocarvalhs.report

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.report.databinding.FragmentReportBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar


@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>() {

    private val viewModel: ReportViewModel by viewModels()

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
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

    private fun displayData() {
        defineValuesReportPay()
        defineValuesReportCosts()
        setupFilters()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(true)
        setupReportPay()
        setupReportCosts()
        setupFilters()
    }

    private fun setupFilters() {
        binding.header.filters.removeAllViews()
        val list = viewModel.defineFilters()
        list.forEach { addFilter(it) }
    }

    private fun addFilter(filter: String?) {
        filter?.let { date ->
            val chip = Chip(requireContext())
            chip.text = date
            chip.setOnClickListener { viewModel.selectedFilter(date) }
            binding.header.filters.addView(chip)
        }
    }

    private fun setupReportCosts() {
        defineValuesReportCosts()
        binding.reportCosts.name.text = getString(R.string.total_costs)
        binding.reportCosts.icon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_trending_down_24
            )
        )
    }

    private fun setupReportPay() {
        defineValuesReportPay()
        binding.reportPay.name.text = getString(R.string.total_pay)
        binding.reportPay.icon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_trending_up_24
            )
        )
    }

    private fun defineValuesReportCosts() {
        binding.reportCosts.value.text = getString(R.string.formated_money, viewModel.totalCosts())
    }

    private fun defineValuesReportPay() {
        binding.reportPay.value.text = getString(R.string.formated_money, viewModel.totalPay())
    }

    override fun loading() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}