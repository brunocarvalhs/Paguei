package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

abstract class BaseFragmentCompose : Fragment() {

    protected abstract fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean = false
    ): View?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        visibilityToolbar()
        initView()
        return createBinding(inflater, container)
    }

    abstract fun initView()

    protected fun visibilityToolbar(visibility: Boolean = false) {
        if (visibility) (requireActivity() as ManagerToolbar).showToolbar()
        else (requireActivity() as ManagerToolbar).hideToolbar()
    }

    protected fun defineAppNavigation(@IdRes layout: Int) {
        (requireActivity() as ManagerToolbar).defineAppNavigation(setOf(layout))
    }
}
