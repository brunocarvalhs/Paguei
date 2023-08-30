package br.com.brunocarvalhs.check_list.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme


@Composable
fun CheckListScreen(
    navController: NavController,
    viewModel: CheckListViewModel
) {
    val uiState = viewModel.state.collectAsState()
    CheckListContent(uiState = uiState.value, onChecked = { viewModel.onSelect(navController, it) })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CheckListContent(uiState: CheckListViewState, onChecked: (String) -> Unit) {
    LazyColumn {
        when (uiState) {
            is CheckListViewState.Error -> {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(fraction = 0.5f)
                    ) {
                        Text(text = uiState.message.orEmpty())
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            CheckListViewState.Loading -> {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(fraction = 0.5f),
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            is CheckListViewState.Success -> {
                val list = uiState.list
                list.forEach { (header, itemList) ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(10.dp)
                        ) {
                            Text(text = header)
                        }
                    }
                    items(itemList.toList()) { (data, isChecked) ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(vertical = 5.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { onChecked.invoke(data.orEmpty()) }
                            )
                            Text(text = data.orEmpty())
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCheckListScreen() {
    PagueiTheme {
        CheckListContent(uiState = CheckListViewState.Loading, onChecked = { })
    }
}