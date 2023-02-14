package br.com.brunocarvalhs.profile.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.profile.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentEditProfileBinding =
        FragmentEditProfileBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {

    }

    override fun loading() {

    }
}