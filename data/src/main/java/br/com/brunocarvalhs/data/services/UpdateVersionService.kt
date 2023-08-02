package br.com.brunocarvalhs.data.services

import android.app.Activity
import br.com.brunocarvalhs.domain.services.UpdateVersionService
import br.com.brunocarvalhs.paguei.data.BuildConfig
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import javax.inject.Inject

class UpdateVersionServiceImpl @Inject constructor() : UpdateVersionService {

    private lateinit var activity: Activity
    private lateinit var type: UpdateVersionService.Type
    private lateinit var appUpdateManager: AppUpdateManager
    private var listener: ((Void) -> Unit)? = null

    private fun showImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE || appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.IMMEDIATE, activity, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    private fun showFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            listener?.invoke(appUpdateManager.completeUpdate().result)
        } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.FLEXIBLE, activity, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    override fun <T : Any> updateApplication(
        activity: T, type: UpdateVersionService.Type, function: (Void) -> Unit
    ) {
        if (activity is Activity) {
            this.activity = activity
            this.type = type
            this.listener = function
            initUpdate()
        }
    }

    private fun initUpdate() {
        if (BuildConfig.DEBUG) {
            appUpdateManager = FakeAppUpdateManager(activity)
            (appUpdateManager as FakeAppUpdateManager).setUpdateAvailable(2)

            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                triggerAppUpdate(appUpdateInfo)
            }
        } else {
            appUpdateManager = AppUpdateManagerFactory.create(activity)
            appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                triggerAppUpdate(appUpdateInfo)
            }
        }
    }

    private fun triggerAppUpdate(appUpdateInfo: AppUpdateInfo) {
        when (type) {
            UpdateVersionService.Type.IMMEDIATE -> showImmediateUpdate(appUpdateInfo)
            UpdateVersionService.Type.FLEXIBLE -> showFlexibleUpdate(appUpdateInfo)
        }
    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 3
    }
}