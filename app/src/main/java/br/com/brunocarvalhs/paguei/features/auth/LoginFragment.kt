package br.com.brunocarvalhs.paguei.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.commons.BaseFragment
import br.com.brunocarvalhs.paguei.R
import br.com.brunocarvalhs.paguei.databinding.FragmentLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.common.Scopes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        viewModel.onSignInResult()
    }

    private val providers by lazy {
        arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build(),
            AuthUI.IdpConfig.GoogleBuilder().setScopes(listOf(Scopes.PROFILE)).build()
        )
    }

    private val signInIntent by lazy {
        AuthUI.getInstance().createSignInIntentBuilder().setTheme(R.style.Theme_Paguei)
            .setAvailableProviders(providers).setIsSmartLockEnabled(false).build()
    }

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, attachToParent)

    override fun viewObservation() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoginViewState.Loading -> loading()
                LoginViewState.Success -> navigateToHome()
                is LoginViewState.Error -> showError(state.message)
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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
        viewModel.onSession()
    }
}
