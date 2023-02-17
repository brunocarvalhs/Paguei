package br.com.brunocarvalhs.report

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.report.databinding.FragmentReportBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint


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
        binding.totalCosts.text = getString(
            R.string.total_costs, getString(R.string.formated_money, viewModel.totalCosts())
        )
        binding.totalPay.text = getString(
            R.string.total_pay, getString(R.string.formated_money, viewModel.totalPay())
        )
        setupChart()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(true)
    }

    override fun loading() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun setupChart() {
        val chart = binding.chart

        val entries = mutableListOf<BarEntry>()
        val valuesFormatter = mutableListOf<String>()
        entries.add(BarEntry(0f, viewModel.totalCosts))
        valuesFormatter.add("Total Pago (R$)")
        entries.add(BarEntry(1f, viewModel.totalPay))
        valuesFormatter.add("Total de Custos (R$)")
        viewModel.totalRender?.let {
            entries.add(BarEntry(2f, it))
            valuesFormatter.add("Total de Renda (R$)")
        }

        val set = BarDataSet(entries, null)
        set.colors = listOf(Color.RED, Color.GREEN, Color.BLUE)

        val data = BarData(set)
        chart.data = data

        val xAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTH_SIDED
        xAxis.textSize = 10f
        xAxis.gridColor = Color.TRANSPARENT
        xAxis.axisLineColor = Color.TRANSPARENT
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(valuesFormatter)

        val description: Description = chart.description
        description.isEnabled = false

        val l: Legend = chart.legend
        l.isEnabled = false
        chart.setVisibleXRangeMaximum(5f)

        chart.setTouchEnabled(false)
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)
        chart.isScaleXEnabled = false
        chart.isScaleYEnabled = false
        chart.setPinchZoom(false)
        chart.isDoubleTapToZoomEnabled = false

        chart.axisRight.isEnabled = true
        chart.axisLeft.isEnabled = false

        chart.animateXY(1000, 1000)
        chart.setPinchZoom(true)
        chart.invalidate()
    }
}