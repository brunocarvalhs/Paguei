package br.com.brunocarvalhs.payflow.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.payflow.R
import br.com.brunocarvalhs.payflow.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthMethodPickerLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.common.Scopes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { viewModel.onSignInResult() }

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().setScopes(listOf(Scopes.PROFILE)).build(),
        AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build(),
        AuthUI.IdpConfig.PhoneBuilder().build()
    )

    private val customLayout = AuthMethodPickerLayout.Builder(R.layout.custom_login_layout_xml)
        .setGoogleButtonId(R.id.button2).setEmailButtonId(R.id.button3)
        .setPhoneButtonId(R.id.button4).build()

    private val signInIntent =
        AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.Theme_PayFlow)
            .setAvailableProviders(providers).setAuthMethodPickerLayout(customLayout)
            .setIsSmartLockEnabled(false).build()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentLoginBinding =
        FragmentLoginBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                LoginViewState.Loading -> this.loading()
                is LoginViewState.Success -> this.navigateToHome(it.user)
                is LoginViewState.Error -> this.showError(it.message)
            }
        }
    }

    private fun navigateToHome(user: UserModel) {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
        findNavController().navigate(action)
    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun initView() {
        binding.button.setOnClickListener {
            signInLauncher.launch(signInIntent)
        }
    }

    override fun loading() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.onSignInResult()
    }
}
