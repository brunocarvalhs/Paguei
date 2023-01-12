package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding, V : ViewModel> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = requireNotNull(_binding)

    private var _viewModel: V? = null
    protected val viewModel get() = requireNotNull(_viewModel)

    protected abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): T
    protected abstract fun createViewModel(viewModelProvider: ViewModelProvider): V

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        initView()
        savedInstanceState?.let { argumentsView(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewModel = createViewModel(ViewModelProvider(this))
        viewObservation()
        savedInstanceState?.let { argumentsView(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun viewObservation()

    abstract fun argumentsView(arguments: Bundle)

    abstract fun initView()

    abstract fun loading()
}
