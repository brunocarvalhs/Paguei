package br.com.brunocarvalhs.costs.costs_list.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R

@Composable
fun BottomNavigation(
    onClickFloatingAction: () -> Unit,
    onHome: (() -> Unit)? = null,
    onExtracts: (() -> Unit)? = null,
    onCalculation: (() -> Unit)? = null,
    onCheckList: (() -> Unit)? = null,
) {
    BottomAppBar(
        actions = {
            onHome?.let {
                IconButton(onClick = onHome) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_home_24),
                        contentDescription = stringResource(id = R.string.menu_text_group),
                    )
                }
            }
            onExtracts?.let {
                IconButton(onClick = onExtracts) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_extracts_24),
                        contentDescription = stringResource(id = R.string.menu_text_extract),
                    )
                }
            }
            onCalculation?.let {
                IconButton(onClick = onCalculation) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_calculate_24),
                        contentDescription = stringResource(id = R.string.menu_text_calculation),
                    )
                }
            }
            onCheckList?.let {
                IconButton(onClick = onCheckList) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
                        contentDescription = stringResource(id = R.string.menu_text_check_list),
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickFloatingAction,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    Icons.Filled.Add,
                    stringResource(id = R.string.fragment_costs_list_button_add_text)
                )
            }
        }
    )
}

@Composable
@Preview
private fun PreviewBottomNavigation() {
    PagueiTheme {
        BottomNavigation(
            onClickFloatingAction = {},
            onHome = { },
            onExtracts = { },
            onCalculation = { },
            onCheckList = { },
        )
    }
}