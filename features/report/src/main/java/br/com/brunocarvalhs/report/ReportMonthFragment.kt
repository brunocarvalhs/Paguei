package br.com.brunocarvalhs.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.AdsService
import br.com.brunocarvalhs.report.databinding.FragmentReportMonthBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReportMonthFragment : BaseFragment<FragmentReportMonthBinding>() {

    private val viewModel: ReportMonthViewModel by viewModels()

    @Inject
    lateinit var adsService: AdsService

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentReportMonthBinding =
        FragmentReportMonthBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ReportMonthViewState.Error -> this.showError(it.message)
                ReportMonthViewState.Loading -> this.loading()
                ReportMonthViewState.Success -> this.displayData()
            }
        }
    }

    private fun displayData() {
        this.setupReportPay()
        this.setupReportCosts()
        this.defineExpenseFrequency()
    }

    override fun argumentsView(arguments: Bundle) {
        viewModel.list = arguments.getSerializable("cost_list") as List<CostEntities>
        viewModel.month = arguments.getString("month")
    }

    override fun initView() {
        visibilityToolbar(true)
        viewModel.fetchData()
        adsService.start()
        adsService.banner(binding.container, getString(R.string.banner_report))
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun setupReportCosts() {
        binding.reportCosts.value.text = getString(R.string.formated_money, viewModel.totalCosts)
        binding.reportCosts.name.text = getString(R.string.total_costs)
        binding.reportCosts.icon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_trending_down_24
            )
        )
    }

    private fun setupReportPay() {
        binding.reportPay.value.text = getString(R.string.formated_money, viewModel.totalPay)
        binding.reportPay.name.text = getString(R.string.total_pay)
        binding.reportPay.icon.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_baseline_trending_up_24
            )
        )
    }

    private fun defineExpenseFrequency() {
        val frequency = viewModel.frequency
        val entries = frequency?.map { (category, count) ->
            PieEntry(count.toFloat(), category)
        }

        val colors = listOf(
            ContextCompat.getColor(
                requireContext(),
                br.com.brunocarvalhs.paguei.commons.R.color.md_theme_light_primary
            ),
            ContextCompat.getColor(
                requireContext(),
                br.com.brunocarvalhs.paguei.commons.R.color.md_theme_light_secondary
            )
        )

        val dataSet = PieDataSet(entries, "Gastos")
        val data = PieData(dataSet)
        dataSet.colors = colors
        binding.pieChart.data = data
        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.description.isEnabled = false
        binding.pieChart.isRotationEnabled = true
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance(list: List<CostEntities>, month: String?) = ReportMonthFragment().apply {
            arguments = Bundle().apply {
                putSerializable("cost_list", ArrayList(list))
                putString("month", month)
            }
        }
    }
}