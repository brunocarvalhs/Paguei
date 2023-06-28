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
import br.com.brunocarvalhs.paguei.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ManagerToolbar {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appUpdateManager: AppUpdateManager

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

    private fun triggerAppUpdate(appUpdateInfo: AppUpdateInfo) {
        showFlexibleUpdate(appUpdateInfo)
    }

    private fun showImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE || appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.IMMEDIATE, this, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    private fun showFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            showInstallAlert(onOKClick = {
                appUpdateManager.completeUpdate()
            })
        } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.FLEXIBLE, this, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    private fun showInstallAlert(onOKClick: () -> Task<Void>) {
        MaterialAlertDialogBuilder(this).setTitle(getString(R.string.update_success))
            .setMessage(getString(R.string.update_message))
            .setNeutralButton(getString(R.string.update_button)) { _, _ ->
                onOKClick.invoke()
            }.show()
    }

    private fun updateApplication() {
        if (BuildConfig.DEBUG) {
            appUpdateManager = FakeAppUpdateManager(this)
            (appUpdateManager as FakeAppUpdateManager).setUpdateAvailable(2)

            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                triggerAppUpdate(appUpdateInfo)
            }
        } else {
            appUpdateManager = AppUpdateManagerFactory.create(this)
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                triggerAppUpdate(appUpdateInfo)
            }
        }
    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 3
    }
}