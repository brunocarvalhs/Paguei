package br.com.brunocarvalhs.profile.read

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.domain.usecase.auth.GetUserSessionUseCase
import br.com.brunocarvalhs.domain.usecase.auth.LogoutUserUseCase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserSessionUseCase: GetUserSessionUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val navigation: Navigation,
    private val analyticsService: AnalyticsService
) : BaseComposeViewModel<ProfileViewState>(ProfileViewState.Default) {

    private var qrCode: Bitmap? = null

    private fun logout(event: () -> Unit) {
        viewModelScope.launch {
            mutableState.value = ProfileViewState.Loading
            logoutUserUseCase.invoke()
                .onSuccess { event() }
                .onFailure { mutableState.value = ProfileViewState.Error(it.message) }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = ProfileViewState.Loading
            getUserSessionUseCase.invoke()
                .onSuccess { user ->
                    mutableState.value = ProfileViewState.Success(user)
                    user?.id.let { id -> qrCode = encodeAsBitmap(id?.reversed()) }
                }
                .onFailure { mutableState.value = ProfileViewState.Error(it.message) }
        }
    }

    fun logout(navController: NavController) {
        this.logout {
            navigateToLogin(navController)

            analyticsService.trackEvent(
                AnalyticsService.Events.LOGOUT,
                mapOf(),
                ProfileFragment::class
            )
        }
    }

    fun navigateToSettings(navController: NavController) {
        val request = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        navController.navigate(request)
    }

    fun navigateToEditProfile(navController: NavController) {
        val request = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        navController.navigate(request)
    }

    private fun navigateToLogin(navController: NavController) {
        val request = navigation.navigateToLoginRegister()
        navController.navigate(request)
    }

    fun visibilityQrCode(context: Context) {
        qrCode?.let {
            val image = ImageView(context)
            image.setImageBitmap(it)
            MaterialAlertDialogBuilder(context)
                .setView(image)
                .setNegativeButton("OK") { _, _ -> }.show()
        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(code: String?): Bitmap? {
        return code?.let {
            val writer = QRCodeWriter()
            val bitMatrix: BitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, 1000, 1000)
            val w: Int = bitMatrix.width
            val h: Int = bitMatrix.height
            val pixels = IntArray(w * h)
            for (y in 0 until h) {
                for (x in 0 until w) {
                    pixels[y * w + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                }
            }
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h)
            bitmap
        }
    }
}