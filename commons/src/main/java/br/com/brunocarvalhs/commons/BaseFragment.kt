package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = requireNotNull(_binding)

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = createBinding(inflater, container)
        arguments?.let { argumentsView(it) }
        visibilityToolbar()
        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewObservation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun viewObservation()

    abstract fun argumentsView(arguments: Bundle)

    abstract fun initView()

    protected fun loading() {

    }

    protected fun showError(message: String?) {
        Snackbar.make(binding.root, message.orEmpty(), Toast.LENGTH_LONG).show()
    }

    protected fun visibilityToolbar(visibility: Boolean = false) {
        if (visibility) (requireActivity() as ManagerToolbar).showToolbar()
        else (requireActivity() as ManagerToolbar).hideToolbar()
    }

    protected fun defineAppNavigation(@IdRes layout: Int) {
        (requireActivity() as ManagerToolbar).defineAppNavigation(setOf(layout))
    }
}
