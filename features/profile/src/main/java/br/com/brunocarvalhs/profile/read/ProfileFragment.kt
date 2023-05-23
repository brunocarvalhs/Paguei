package br.com.brunocarvalhs.profile.read

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.domain.services.AnalyticsService
import br.com.brunocarvalhs.profile.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    @Inject
    lateinit var navigation: Navigation
    private val viewModel: ProfileViewModel by viewModels()
    private val glide by lazy { Glide.with(this) }
    private var qrCode: Bitmap? = null

    @Inject
    lateinit var analyticsService: AnalyticsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsService.trackScreenView(
            ProfileFragment::class.simpleName.orEmpty(),
            ProfileFragment::class
        )
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is ProfileViewState.Error -> this.showError(it.message)
                ProfileViewState.Loading -> this.loading()
                is ProfileViewState.Success -> this.displayData(it.user)
            }
        }
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        binding.logout.setOnClickListener { logout() }
        binding.settings.setOnClickListener { navigateToSettings() }
        binding.editProfile.setOnClickListener { navigateToEditProfile() }
        binding.qrCode.visibility = View.GONE
        binding.qrCode.setOnClickListener { visibilityQrCode() }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchData()
    }

    private fun displayData(user: UserEntities?) {
        user?.let {
            user.photoUrl?.let { photoUrl ->
                glide.load(photoUrl).centerCrop().into(binding.avatar)
            } ?: kotlin.run {
                binding.avatar.visibility = View.GONE
                binding.avatarText.visibility = View.VISIBLE
                binding.avatarText.text = user.initialsName()
            }
            binding.name.text = user.name
            binding.contact.text = user.email
            qrCode = encodeAsBitmap(user.id.reversed())
            binding.qrCode.visibility = if (qrCode != null) View.VISIBLE else View.GONE
        }
    }

    private fun logout() {
        viewModel.logout { navigateToLogin() }
    }

    private fun navigateToSettings() {
        val request = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        findNavController().navigate(request)
    }

    private fun navigateToEditProfile() {
        val request = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        findNavController().navigate(request)
    }

    private fun navigateToLogin() {
        val request = navigation.navigateToLoginRegister()
        findNavController().navigate(request)
    }

    private fun visibilityQrCode() {
        qrCode?.let {
            val image = ImageView(requireContext())
            image.setImageBitmap(it)
            MaterialAlertDialogBuilder(requireContext())
                .setView(image)
                .setNegativeButton("OK") { _, _ ->
                    findNavController().popBackStack()
                }.show()
        }
    }

    @Throws(WriterException::class)
    fun encodeAsBitmap(code: String): Bitmap? {
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
        return bitmap
    }
}