package br.com.brunocarvalhs.calculation.resume

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.brunocarvalhs.calculation.R

class CalculationResumeFragment : Fragment() {

    companion object {
        fun newInstance() = CalculationResumeFragment()
    }

    private lateinit var viewModel: CalculationResumeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculation_resume, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculationResumeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}