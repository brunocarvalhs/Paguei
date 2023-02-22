package br.com.brunocarvalhs.profile.read

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.profile.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    @Inject
    lateinit var navigation: Navigation
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
        binding.about.setOnClickListener { navigateToAbort() }
        binding.settings.setOnClickListener { navigateToSettings() }
        binding.editProfile.setOnClickListener { navigateToEditProfile() }
    }

    override fun loading() {

    }

    private fun logout() {
        viewModel.logout()
        navigateToLogin()
    }

    private fun navigateToSettings() {
        val request = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        findNavController().navigate(request)
    }

    private fun navigateToEditProfile() {
        val request = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
        findNavController().navigate(request)
    }

    private fun navigateToAbort() {
        val request = ProfileFragmentDirections.actionProfileFragmentToAbortFragment()
        findNavController().navigate(request)
    }

    private fun navigateToLogin() {
        val request = navigation.navigateToLoginRegister()
        findNavController().navigate(request)
    }
}