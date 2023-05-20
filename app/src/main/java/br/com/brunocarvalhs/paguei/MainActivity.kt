package br.com.brunocarvalhs.paguei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.brunocarvalhs.commons.ManagerToolbar
import br.com.brunocarvalhs.paguei.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ManagerToolbar {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var listener: InstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        updateApplication()
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

    override fun onResume() {
        super.onResume()
        appUpdateManager = AppUpdateManagerFactory.create(this)
        listener = InstallStateUpdatedListener { state ->
            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                popupSnackbarForCompleteUpdate()
            }
        }
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
            } else {
                upgrade(appUpdateInfo)
            }
        }
        appUpdateManager.registerListener(listener)
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            binding.root,
            "An update has just been downloaded.",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("RESTART") { appUpdateManager.completeUpdate() }
            show()
        }
    }

    private fun upgrade(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
        ) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.FLEXIBLE,
                this,
                REQUEST_UPDATE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_UPDATE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(listener)
    }

    private fun updateApplication() {
        appUpdateManager = AppUpdateManagerFactory.create(this)
    }

    companion object {
        private const val REQUEST_UPDATE = 3456
    }
}