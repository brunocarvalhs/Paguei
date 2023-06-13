package br.com.brunocarvalhs.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.report.databinding.FragmentReportBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReportFragment : BaseFragment<FragmentReportBinding>() {

    private val viewModel: ReportViewModel by viewModels()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ReportFragmentStateAdapter

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            ReportFragment::class.simpleName.orEmpty(), ReportFragment::class
        )
    }

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

    override fun argumentsView(arguments: Bundle) {

    }

    private fun displayData() {
        defineChart()
        setupFilters()
    }

    override fun initView() {
        visibilityToolbar(true)
        viewPager = binding.myPager
        adapter = ReportFragmentStateAdapter(this)
        setupFilters()
    }

    private fun defineChart() {
        val costs = viewModel.defineBarChart()

        if (costs.isNotEmpty()) {
            val entries = mutableListOf<BarEntry>()
            for ((index, cost) in costs.withIndex()) {
                val xValue = index.toFloat()
                val yValue = cost.totalValue.toFloat()
                entries.add(BarEntry(xValue, yValue))
            }

            val dataSet = BarDataSet(entries, "Custos Mensais")
            dataSet.setDrawValues(false)

            val data = BarData(dataSet)

            val xAxis = binding.lineChart.xAxis
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.lineChartColor)

            val leftYAxis = binding.lineChart.axisLeft
            leftYAxis.textColor = ContextCompat.getColor(requireContext(), R.color.lineChartColor)

            val rightYAxis = binding.lineChart.axisRight
            rightYAxis.textColor = ContextCompat.getColor(requireContext(), R.color.lineChartColor)

            binding.lineChart.data = data
            binding.lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(costs.map { it.date })
            binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.lineChart.animateY(1000)
            binding.lineChart.isHorizontalScrollBarEnabled = true
            binding.lineChart.isVerticalScrollBarEnabled = false
            binding.lineChart.invalidate()
        }
    }

    private fun setupFilters() {
        val list = viewModel.defineFilters()

        viewPager.adapter = adapter

        list.forEach { item ->
            adapter.addFragment(ReportMonthFragment.newInstance(item.value, item.key))
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