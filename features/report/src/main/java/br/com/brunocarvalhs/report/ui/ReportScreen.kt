package br.com.brunocarvalhs.report.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.data.utils.moneyFormatted
import br.com.brunocarvalhs.data.utils.sumOfFormatted

@Composable
fun ReportScreen(navController: NavController, viewModel: ReportViewModel) {
    val uiState = viewModel.state.collectAsState()
    ReportContent(
        uiState = uiState.value,
        onLeft = { viewModel.onPreviousMonth() },
        onRight = { viewModel.onNextMonth() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReportContent(
    uiState: ReportViewState,
    onLeft: () -> Unit,
    onRight: () -> Unit,
) {
    LazyColumn {

        when (uiState) {
            is ReportViewState.Error -> {
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

            ReportViewState.Loading -> {
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

            is ReportViewState.Success -> {
                stickyHeader {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(10.dp)
                    ) {
                        IconButton(onClick = onLeft) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIos,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface
                            )
                        }
                        Text(text = uiState.date)
                        IconButton(onClick = onRight) {
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inverseSurface
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .padding(vertical = 15.dp, horizontal = 10.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(120.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    style = MaterialTheme.typography.labelSmall,
                                    text = "Fixo",
                                )
                                Text(
                                    style = MaterialTheme.typography.headlineSmall,
                                    text = uiState.list[uiState.date]?.sumOfFormatted { it.type == "FIX" }
                                        ?: (0.0).moneyFormatted()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .height(120.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceAround,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    style = MaterialTheme.typography.labelSmall,
                                    text = "Variável",
                                )
                                Text(
                                    style = MaterialTheme.typography.headlineSmall,
                                    text = uiState.list[uiState.date]?.sumOfFormatted { it.type == "VARIABLE" }
                                        ?: (0.0).moneyFormatted(),
                                )
                            }
                        }
                    }
                }
                items(uiState.list[uiState.date].orEmpty(), key = { it.id }) {
                    Row {
                        Text(text = it.name.orEmpty())
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewReportScreen() {
    PagueiTheme {
        ReportContent(
            uiState = ReportViewState.Loading,
            onLeft = { },
            onRight = { }
        )
    }
}