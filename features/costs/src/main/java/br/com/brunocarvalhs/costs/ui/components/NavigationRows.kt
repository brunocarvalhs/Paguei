package br.com.brunocarvalhs.costs.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R

@Composable
fun NavigationRows(
    onHome: (() -> Unit)? = null,
    onExtracts: (() -> Unit)? = null,
    onCalculation: (() -> Unit)? = null,
    onCheckList: (() -> Unit)? = null,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        onHome?.let {
            item {
                NavigationRowsItem(
                    onClick = onHome,
                    painter = painterResource(id = R.drawable.ic_baseline_home_24),
                    name = stringResource(id = R.string.menu_text_group)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        onExtracts?.let {
            item {
                NavigationRowsItem(
                    onClick = onExtracts,
                    painter = painterResource(id = R.drawable.ic_baseline_extracts_24),
                    name = stringResource(id = R.string.menu_text_extract),
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        onCalculation?.let {
            item {
                NavigationRowsItem(
                    onClick = onCalculation,
                    painter = painterResource(id = R.drawable.ic_baseline_calculate_24),
                    name = stringResource(id = R.string.menu_text_calculation),
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
        onCheckList?.let {
            item {
                NavigationRowsItem(
                    onClick = onCheckList,
                    painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
                    name = stringResource(id = R.string.menu_text_check_list),
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Composable
@Preview
private fun PreviewNavigationRows() {
    PagueiTheme {
        NavigationRows(
            onHome = { },
            onExtracts = { },
            onCalculation = { },
            onCheckList = { },
        )
    }
}