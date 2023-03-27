package br.com.brunocarvalhs.extracts.extracts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.annotation.DevicesPreview
import br.com.brunocarvalhs.commons.annotation.DevicesPreviewDark
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.data.model.CostsModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.extracts.R

@Composable
fun ExtractLayout(viewModel: ExtractComposeViewModel) {
    DisposableEffect(Unit) {
        viewModel.fetchData()
        onDispose { }
    }

    val state by viewModel.state.collectAsState()

    ExtractScreen(state = state)
}

@Composable
private fun ExtractScreen(state: ExtractViewState) {
    Scaffold(topBar = { SearchBar() }, content = {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            color = Color(0xFFEAEAEA),
            content = {
                when (state) {
                    is ExtractViewState.Error -> ExtractError(state.message)
                    ExtractViewState.Loading -> ExtractLoading()
                    is ExtractViewState.Success -> ExtractList(state.list)
                }
            })
    })
}

@Composable
fun ExtractError(message: String?) {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        Text(text = message.orEmpty())
    }
}

@Composable
fun ExtractLoading() {
    Box(Modifier.fillMaxSize(), Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun SearchBar() {
    TopAppBar(modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFF3F51B5),
        contentColor = Color.White,
        elevation = 4.dp,
        title = {
            Text(text = "Search")
        },
        actions = {
            IconButton(onClick = { /* Do Something */ }) {
                Icon(Icons.Filled.Search, "Search")
            }
        })
}

@Composable
fun ExtractList(costs: List<CostEntities>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(costs) { cost ->
            ExtractListItem(cost)
        }
    }
}

@Composable
fun ExtractListItem(cost: CostEntities) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = cost.name.toString(),
                style = MaterialTheme.typography.h6,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Text(
                text = stringResource(
                    R.string.fragment_extracts_item_cost_date, cost.datePayment.toString()
                ), style = MaterialTheme.typography.caption, modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = stringResource(R.string.item_cost_value, cost.formatValue()),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .wrapContentWidth(align = androidx.compose.ui.Alignment.End)
            )
        }
    }
}

@DevicesPreview
@DevicesPreviewDark
@Composable
private fun PreviewLoadingExtractScreen() {
    PagueiTheme {
        Surface {
            ExtractScreen(state = ExtractViewState.Loading)
        }
    }
}

@DevicesPreview
@DevicesPreviewDark
@Composable
private fun PreviewErrorExtractScreen() {
    PagueiTheme {
        Surface {
            ExtractScreen(state = ExtractViewState.Error("Mensagem de erro!"))
        }
    }
}

@DevicesPreview
@DevicesPreviewDark
@Composable
private fun PreviewListExtractScreen() {
    PagueiTheme {
        Surface {
            ExtractScreen(state = ExtractViewState.Success((1..100).map {
                CostsModel(
                    name = "Teste $it", value = "1000.00", datePayment = "01/01/0001"
                )
            }))
        }
    }
}