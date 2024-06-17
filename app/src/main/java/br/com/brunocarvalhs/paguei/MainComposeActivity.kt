package br.com.brunocarvalhs.paguei

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.brunocarvalhs.auth.feature.presentation.navigation.authGraph
import br.com.brunocarvalhs.commons.navigation.NavigationItem
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.navigation.homeGraph
import br.com.brunocarvalhs.domain.services.UpdateVersionService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {

    @Inject
    lateinit var updateVersionService: UpdateVersionService

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PagueiTheme {
                val navigator = rememberNavController()

                val isTopLevelDestination =
                    navigator.currentBackStackEntryAsState().value?.destination?.route in NavigationItem.topLevelDestinations.map { it.route }

                val isBottomNavigationDestination =
                    navigator.currentBackStackEntryAsState().value?.destination?.route in NavigationItem.bottomNavigation.map { it.route }

                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = { }, navigationIcon = {
                        if (!isTopLevelDestination) {
                            IconButton(onClick = { navigator.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        }
                    })
                }, bottomBar = {
                    if (isBottomNavigationDestination) {
                        BottomAppBar {
                            NavigationItem.bottomNavigation.forEach {
                                BottomNavigationItem(
                                    selected = navigator.currentDestination?.route == it.route,
                                    onClick = { navigator.navigate(it.route) },
                                    icon = {
                                        it.icon?.let { icon ->
                                            Icon(
                                                icon,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }) {
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                    ) {
                        NavHost(
                            navController = navigator,
                            startDestination = NavigationItem.START
                        ) {
                            authGraph(navController = navigator, route = NavigationItem.Login.route)
                            homeGraph(navController = navigator, route = NavigationItem.Home.route)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setupUpdate()
    }

    private fun setupUpdate() {
        updateVersionService.updateApplication(
            activity = this, type = UpdateVersionService.Type.IMMEDIATE
        ) {
            showInstallAlert { it }
        }
    }

    private fun showInstallAlert(onOKClick: () -> Void) {
        MaterialAlertDialogBuilder(this).setTitle(getString(R.string.update_success))
            .setMessage(getString(R.string.update_message))
            .setNeutralButton(getString(R.string.update_button)) { _, _ ->
                onOKClick.invoke()
            }.show()
    }
}