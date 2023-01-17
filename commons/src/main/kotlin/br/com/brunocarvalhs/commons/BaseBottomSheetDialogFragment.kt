package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: T? = null
    protected val binding get() = requireNotNull(_binding)

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        initView()
        return binding.root
    }

    abstract fun initView()

    abstract fun viewObservation()

    abstract fun argumentsView(arguments: Bundle)

    abstract fun loading()

    protected fun showError(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewObservation()
        savedInstanceState?.let { argumentsView(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}