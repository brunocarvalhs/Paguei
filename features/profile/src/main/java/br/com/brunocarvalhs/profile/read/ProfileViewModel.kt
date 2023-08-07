package br.com.brunocarvalhs.profile.read

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
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

    fun logout(fragment: Fragment) {
        this.logout {
            navigateToLogin(fragment)

            analyticsService.trackEvent(
                AnalyticsService.Events.LOGOUT,
                mapOf(),
                ProfileFragment::class
            )
        }
    }

    fun navigateToSettings(fragment: Fragment) {
        val request = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        fragment.findNavController().navigate(request)
    }

    fun navigateToEditProfile(fragment: Fragment) {
        val request = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        fragment.findNavController().navigate(request)
    }

    private fun navigateToLogin(fragment: Fragment) {
        val request = navigation.navigateToLoginRegister()
        fragment.findNavController().navigate(request)
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