package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import br.com.brunocarvalhs.commons.theme.PagueiTheme

abstract class BaseComposeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        visibilityToolbar()
        return ComposeView(requireContext()).apply {
            setContent {
                PagueiTheme {
                    createScreen()
                }
            }
        }
    }

    protected fun visibilityToolbar(visibility: Boolean = false) {
        if (visibility) (requireActivity() as ManagerToolbar).showToolbar()
        else (requireActivity() as ManagerToolbar).hideToolbar()
    }

    protected fun defineAppNavigation(@IdRes layout: Int) {
        (requireActivity() as ManagerToolbar).defineAppNavigation(setOf(layout))
    }

    @Composable
    abstract fun createScreen()
}
