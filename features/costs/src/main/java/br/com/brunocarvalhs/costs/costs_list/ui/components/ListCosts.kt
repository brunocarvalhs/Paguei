package br.com.brunocarvalhs.costs.costs_list.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.domain.entities.CostEntities

@Composable
fun ListCosts(
    list: List<CostEntities>,
    modifier: Modifier = Modifier,
    onClick: (cost: CostEntities) -> Unit,
    onLongClick: (cost: CostEntities) -> Unit,
    onLeft: (cost: CostEntities) -> Unit,
    onRight: (cost: CostEntities) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(list) { cost ->
            CostItem(
                cost = cost,
                onClick = onClick,
                onLongClick = onLongClick,
                onLeft = onLeft,
                onRight = onRight
            )
        }
    }
}

@Composable
@Preview
private fun PreviewListCosts() {
    PagueiTheme {
        ListCosts(list = listOf(), onClick = {}, onRight = {}, onLeft = {}, onLongClick = {})
    }
}