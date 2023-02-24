package br.com.brunocarvalhs.profile.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.profile.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var navigation: Navigation

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)
        setupCategories()
    }

    private fun setupCategories() {
        setupNotifications()
        setupAnalytics()
        setupAccount()
        setupFeedback()
    }

    private fun setupNotifications() {
        val notifications: SwitchPreferenceCompat? = findPreference("notifications")
        notifications?.setOnPreferenceChangeListener { preference, newValue ->
            viewModel.dataStore.put(preference.key, newValue.toString())
            true
        }
        notifications?.callChangeListener(viewModel.dataStore.get("notifications", true))
    }

    private fun setupFeedback() {
        val feedback: Preference? = findPreference("feedback")
        feedback?.setOnPreferenceClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://github.com/brunocarvalhs/Paguei/issues/new/choose")
            requireActivity().startActivity(openURL)
            true
        }
    }

    private fun setupAccount() {
        val account: Preference? = findPreference("account")
        account?.setOnPreferenceClickListener {
            dialogDeleteData()
            true
        }
    }

    private fun setupAnalytics() {
        val analytics: SwitchPreferenceCompat? = findPreference("analytics")
        analytics?.setOnPreferenceChangeListener { preference, newValue ->
            viewModel.dataStore.put(preference.key, newValue.toString())
            true
        }
        analytics?.callChangeListener(viewModel.dataStore.get("analytics", true))
    }

    private fun dialogDeleteData() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.settings_delete_account_title))
            .setMessage(resources.getString(R.string.settings_delete_account_summary))
            .setNegativeButton(resources.getString(R.string.settings_delete_account_dialog_decline)) { dialog, _ -> dialog.cancel() }
            .setPositiveButton(resources.getString(R.string.settings_delete_account_dialog_accept)) { _, _ -> deleteData() }
            .show()
    }

    private fun deleteData() {
        navigation.navigateToLoginRegister()
        viewModel.deleteData()
    }
}