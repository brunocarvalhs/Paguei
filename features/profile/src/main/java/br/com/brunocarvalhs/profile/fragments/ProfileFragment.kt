package br.com.brunocarvalhs.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.profile.databinding.FragmentProfileBinding
import br.com.brunocarvalhs.profile.viewmodels.ProfileViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    private val glide by lazy { Glide.with(this) }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        visibilityToolbar(visibility = true)
        viewModel.user?.let {
            it.photoUrl?.let { photoUrl ->
                glide.load(photoUrl).centerCrop().into(binding.avatar)
            } ?: kotlin.run {
                binding.avatar.visibility = View.GONE
                binding.avatarText.visibility = View.VISIBLE
                binding.avatarText.text = it.initialsName()
            }

            binding.name.text = it.name
            binding.contact.text = it.email
        }
        binding.logout.setOnClickListener { logout() }
    }

    override fun loading() {

    }

    private fun logout() {
        viewModel.logout()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val request = NavDeepLinkRequest.Builder
            .fromUri("android-app://paguei.app/login_fragment".toUri())
            .build()

        findNavController().navigate(request)
    }
}