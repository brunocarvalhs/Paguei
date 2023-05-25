package br.com.brunocarvalhs.calculation.accounts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.brunocarvalhs.calculation.R

class CalculationAccountsSelectsFragment : Fragment() {

    companion object {
        fun newInstance() = CalculationAccountsSelectsFragment()
    }

    private lateinit var viewModel: CalculationAccountsSelectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculation_accounts_selects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculationAccountsSelectsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}