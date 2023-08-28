package br.com.brunocarvalhs.costs.ui.components

import androidx.compose.material3.BottomAppBar
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
    )
}

@Composable
@Preview
private fun PreviewBottomNavigation() {
    PagueiTheme {
        BottomNavigation(
            onHome = { },
            onExtracts = { },
            onCalculation = { },
            onCheckList = { },
        )
    }
}