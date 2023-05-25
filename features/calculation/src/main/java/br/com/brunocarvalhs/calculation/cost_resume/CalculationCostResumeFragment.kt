package br.com.brunocarvalhs.calculation.cost_resume

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.brunocarvalhs.calculation.R

class CalculationCostResumeFragment : Fragment() {

    companion object {
        fun newInstance() = CalculationCostResumeFragment()
    }

    private lateinit var viewModel: CalculationCostResumeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculation_cost_resume, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculationCostResumeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}