package br.com.brunocarvalhs.costs.ui.costs_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.costs.ui.components.CostItem
import br.com.brunocarvalhs.costs.ui.components.NavigationRows
import br.com.brunocarvalhs.costs.ui.components.TopHeader
import br.com.brunocarvalhs.domain.entities.CostEntities

@Composable
fun CostsScreen(navController: NavController, viewModel: CostsViewModel) {
    val uiState = viewModel.state.collectAsState()
    val context = LocalContext.current

    CostsContent(
        uiState = uiState.value,
        session = viewModel.header,
        onReload = { viewModel.fetchData() },
        onReport = { viewModel.navigateToReport(navController) },
        onProfile = { viewModel.navigateToProfile(navController) },
        onAdd = { viewModel.navigateToAddCosts(navController) },
        onGroups = { viewModel.navigateToGroups(navController) },
        onExtracts = { viewModel.navigateToExtracts(navController) },
        onCalculation = { viewModel.navigateToCalculation(navController) },
        onCheckList = { viewModel.navigateToCheckList(navController) },
        onCost = { viewModel.onClick(it, navController) },
        onLongCost = { viewModel.onLongClick(it, navController) },
        onCostLeft = { viewModel.onSwipeLeft(it, navController) },
        onCostRight = { viewModel.onSwipeRight(context, it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun CostsContent(
    session: CostsViewModel.Header? = null,
    uiState: CostsViewState,
    onAdd: () -> Unit = {},
    onProfile: () -> Unit,
    onReload: () -> Unit = {},
    onReport: () -> Unit = {},
    onGroups: (() -> Unit)? = null,
    onExtracts: (() -> Unit)? = null,
    onCalculation: (() -> Unit)? = null,
    onCheckList: (() -> Unit)? = null,
    onCost: ((CostEntities) -> Unit)? = null,
    onLongCost: ((CostEntities) -> Unit)? = null,
    onCostLeft: ((CostEntities) -> Unit)? = null,
    onCostRight: ((CostEntities) -> Unit)? = null,
) {
    Scaffold { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                userScrollEnabled = uiState is CostsViewState.Success,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    TopHeader(
                        name = session?.name.orEmpty(),
                        photoUrl = session?.photoUrl,
                        isGroup = session?.isGroup == true,
                        onClickMenu = onProfile,
                        onAdd = onAdd,
                        onReport = onReport,
                        list = if (uiState is CostsViewState.Success) uiState.list else emptyList()
                    )
                }

                item {
                    NavigationRows(
                        onHome = onGroups,
                        onExtracts = onExtracts,
                        onCalculation = if (session?.isGroup == true) onCalculation else null,
                        onCheckList = onCheckList,
                    )
                }

                when (uiState) {
                    is CostsViewState.Error -> {
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
                                Button(onClick = onReload) {
                                    Text(text = "Recarregar")
                                }
                            }
                        }
                    }

                    CostsViewState.Loading -> {
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

                    is CostsViewState.Success -> {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(10.dp)
                            ) {
                                Text(text = stringResource(R.string.costs_list_header_title))
                            }
                        }
                        items(uiState.list, key = { it.id }) { item ->
                            Row(
                                Modifier
                                    .animateItemPlacement()
                                    .padding(vertical = 5.dp)) {
                                CostItem(
                                    cost = item,
                                    onClick = { cost -> onCost?.invoke(cost) },
                                    onLongClick = { cost -> onLongCost?.invoke(cost) },
                                    onLeft = { cost -> onCostLeft?.invoke(cost) },
                                    onRight = { cost -> onCostRight?.invoke(cost) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCostsContent() {
    PagueiTheme {
        CostsContent(
            onProfile = {},
            uiState = CostsViewState.Loading,
        )
    }
}