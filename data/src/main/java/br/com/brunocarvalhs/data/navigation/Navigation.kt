package br.com.brunocarvalhs.data.navigation

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.paguei.data.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Navigation @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sessionManager: SessionManager
) {

    private fun createDeeplink(uri: Uri) = NavDeepLinkRequest.Builder.fromUri(uri).build()

    private fun validationAuth(onSuccess: () -> NavDeepLinkRequest): NavDeepLinkRequest {
        return if (sessionManager.isLoggedIn()) {
            onSuccess.invoke()
        } else {
            navigateToLoginRegister()
        }
    }

    fun navigateToProfileRegister(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(
                context.getString(R.string.deeplink_profile).toUri()
            )
        }
    }

    fun navigateToGroupsRegister(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_group_register).toUri())
        }
    }

    fun navigateToGroups(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_groups).toUri())
        }
    }

    fun navigateToCostsRegister(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_costs).toUri())
        }
    }

    fun navigateToLoginRegister(): NavDeepLinkRequest {
        return createDeeplink(context.getString(R.string.deeplink_login).toUri())
    }

    fun navigateToExtractsList(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_extracts).toUri())
        }
    }

    fun navigateToBilletRegistrationBarcodeScanner(): NavDeepLinkRequest {
        return createDeeplink(
            context.getString(R.string.deeplink_billet_registration_barcode_scanner).toUri()
        )
    }

    fun navigateToBilletRegistrationForm(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_billet_registration_form).toUri())
        }
    }

    fun navigateToReport(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_report).toUri())
        }
    }

    fun navigateToCalculation(): NavDeepLinkRequest {
        return validationAuth {
            createDeeplink(context.getString(R.string.deeplink_calculation).toUri())
        }
    }
}