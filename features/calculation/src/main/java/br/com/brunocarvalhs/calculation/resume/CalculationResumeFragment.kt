package br.com.brunocarvalhs.calculation.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.brunocarvalhs.calculation.R
import br.com.brunocarvalhs.calculation.databinding.FragmentCalculationResumeBinding
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.AdsService
import br.com.brunocarvalhs.domain.services.AnalyticsService
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculationResumeFragment : BaseFragment<FragmentCalculationResumeBinding>() {

    @Inject
    lateinit var analyticsService: AnalyticsService

    @Inject
    lateinit var adsService: AdsService

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: CalculationResumeViewModel by viewModels()
    private val adapter by lazy { CalculationResumeMembersRecyclerViewAdapter(requireContext()) }
    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentCalculationResumeBinding =
        FragmentCalculationResumeBinding.inflate(inflater, container, attachToParent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            CalculationResumeFragment::class.simpleName.orEmpty(),
            CalculationResumeFragment::class
        )
    }

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

        val dataSet = PieDataSet(entries, getString(R.string.calculation_resume_label))
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
        binding.add.setOnClickListener {
            navigateToCost()

            analyticsService.trackEvent(
                AnalyticsService.Events.BUTTON_CLICKED,
                mapOf(Pair("button_name", "finish")),
                CalculationResumeFragment::class
            )
        }
    }

    private fun navigateToCost() {
        val action = navigation.navigateToCosts()
        findNavController().navigate(action)
    }

    private fun setupList() {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    override fun onResume() {
        super.onResume()
        adsService.fullBanner(requireActivity())
    }
}