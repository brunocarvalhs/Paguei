package br.com.brunocarvalhs.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.report.databinding.FragmentReportMonthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportMonthFragment : BaseFragment<FragmentReportMonthBinding>() {

    private val viewModel: ReportMonthViewModel by viewModels()

    private var list: List<CostEntities> = emptyList()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentReportMonthBinding =
        FragmentReportMonthBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {
        list = arguments.getSerializable("cost_list") as List<CostEntities>
    }

    override fun initView() {
        setupReportPay()
        setupReportCosts()
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
        binding.reportCosts.value.text =
            getString(R.string.formated_money, viewModel.totalCosts(list))
    }

    private fun defineValuesReportPay() {
        binding.reportPay.value.text =
            getString(R.string.formated_money, viewModel.totalPay(list))
    }

    companion object {
        @JvmStatic
        fun newInstance(list: List<CostEntities>) = ReportMonthFragment().apply {
            arguments = Bundle().apply {
                putSerializable("cost_list", ArrayList(list))
            }
        }
    }
}