package br.com.brunocarvalhs.costs.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRowsItem(onClick: () -> Unit, painter: Painter, name: String?) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(100.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.size(100.dp)
        ) {
            Icon(
                painter = painter,
                contentDescription = name,
            )
            Text(text = name.orEmpty())
        }
    }
}

@Composable
@Preview
private fun PreviewNavigationRowsItem() {
    PagueiTheme {
        NavigationRowsItem(
            painter = painterResource(id = R.drawable.ic_baseline_checklist_24),
            name = stringResource(id = R.string.menu_text_check_list),
            onClick = {}
        )
    }
}