package br.com.brunocarvalhs.calculation.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationResumeBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.domain.entities.UserEntities
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculationResumeFragment : BaseFragment<FragmentCalculationResumeBinding>() {

    private val viewModel: CalculationResumeViewModel by viewModels()
    private val adapter by lazy { CalculationResumeMembersRecyclerViewAdapter(requireContext()) }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCalculationResumeBinding =
        FragmentCalculationResumeBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is CalculationResumeViewState.Error -> this.showError(it.message)
                CalculationResumeViewState.Loading -> this.loading()
                is CalculationResumeViewState.Success -> this.displayData(it.percentagesToMembers)
            }
        }
    }

    private fun displayData(list: HashMap<UserEntities, Double>) {
        adapter.submitList(list)
        defineExpenseFrequency(list)
    }

    private fun defineExpenseFrequency(list: HashMap<UserEntities, Double>) {
        val entries = list.map { (user, percent) ->
            PieEntry(percent.toFloat(), user.initialsName())
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

        val dataSet = PieDataSet(entries, "Porcentagem por membro")
        val data = PieData(dataSet)
        dataSet.colors = colors
        binding.pieChart.data = data
        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.description.isEnabled = false
        binding.pieChart.isRotationEnabled = true
        binding.pieChart.animateY(1000)
        binding.pieChart.invalidate()
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        this.visibilityToolbar(true)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupList()
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }
}