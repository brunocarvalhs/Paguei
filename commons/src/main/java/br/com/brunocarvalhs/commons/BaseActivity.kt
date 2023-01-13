package br.com.brunocarvalhs.commons

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), ManagerToolbar {

    private var _binding: T? = null
    protected val binding get() = requireNotNull(_binding)

    protected abstract fun createBinding(inflater: LayoutInflater): T

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = createBinding(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupNavigation()
    }

    abstract fun setupToolbar()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setupNavigation()

    open fun defineToolbar(): Toolbar? = null

    open fun defineContentHeader(): View? = null

    open fun defineBottomNavigation(): View? = null
}
