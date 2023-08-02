package br.com.brunocarvalhs.data.services

import android.app.Activity
import br.com.brunocarvalhs.paguei.data.BuildConfig
import com.google.android.gms.tasks.Task
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class UpdateVersionService {

    private lateinit var activity: Activity
    private lateinit var type: Type
    private lateinit var appUpdateManager: AppUpdateManager
    private var listener: ((Task<Void>) -> Unit)? = null

    private fun showImmediateUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE || appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.IMMEDIATE, activity, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    private fun showFlexibleUpdate(appUpdateInfo: AppUpdateInfo) {
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            listener?.invoke(appUpdateManager.completeUpdate())
        } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS || appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo, AppUpdateType.FLEXIBLE, activity, APP_UPDATE_REQUEST_CODE
            )
        }
    }

    fun updateApplication(
        activity: Activity,
        type: Type = Type.FLEXIBLE,
        function: (Task<Void>) -> Unit
    ) {
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
        this.activity = activity
        this.type = type
        this.listener = function
    }

    private fun triggerAppUpdate(appUpdateInfo: AppUpdateInfo) {
        when (type) {
            Type.IMMEDIATE -> showImmediateUpdate(appUpdateInfo)
            Type.FLEXIBLE -> showFlexibleUpdate(appUpdateInfo)
        }
    }

    enum class Type {
        IMMEDIATE,
        FLEXIBLE
    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 3
    }
}