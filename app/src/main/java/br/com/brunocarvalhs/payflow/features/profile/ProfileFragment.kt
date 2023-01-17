package br.com.brunocarvalhs.payflow.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.payflow.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

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
                Glide.with(this).load(photoUrl).centerCrop().into(binding.avatar)
            } ?: kotlin.run {
                binding.avatar.visibility = View.GONE
                binding.avatarText.visibility = View.VISIBLE
                binding.avatarText.text = it.initialsName()
            }

            binding.name.text = it.name
            binding.contact.text = it.email ?: it.phoneNumber
        }
    }

    override fun loading() {

    }
}