package br.com.brunocarvalhs.paguei

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.brunocarvalhs.commons.ManagerToolbar
import br.com.brunocarvalhs.domain.services.UpdateVersionService
import br.com.brunocarvalhs.paguei.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ManagerToolbar {

    @Inject
    lateinit var updateVersionService: UpdateVersionService

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setupUpdate()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(navController.graph.startDestinationId))
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    override val toolbar: Toolbar
        get() = binding.topAppBar

    override fun showToolbar() {
        toolbar.visibility = View.VISIBLE
    }

    override fun hideToolbar() {
        toolbar.visibility = View.GONE
    }

    override fun defineAppNavigation(setOf: Set<Int>) {
        val appBarConfiguration = AppBarConfiguration(setOf)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController.handleDeepLink(intent)
    }

    private fun setupUpdate() {
        updateVersionService.updateApplication(
            activity = this, type = UpdateVersionService.Type.IMMEDIATE
        ) {
            showInstallAlert { it }
        }
    }

    private fun showInstallAlert(onOKClick: () -> Void) {
        MaterialAlertDialogBuilder(this).setTitle(getString(R.string.update_success))
            .setMessage(getString(R.string.update_message))
            .setNeutralButton(getString(R.string.update_button)) { _, _ ->
                onOKClick.invoke()
            }.show()
    }
}