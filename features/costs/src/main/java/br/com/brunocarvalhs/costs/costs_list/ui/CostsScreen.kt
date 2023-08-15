package br.com.brunocarvalhs.costs.costs_list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.costs_list.CostsViewModel
import br.com.brunocarvalhs.costs.costs_list.CostsViewState
import br.com.brunocarvalhs.costs.costs_list.ui.components.BottomNavigation
import br.com.brunocarvalhs.costs.costs_list.ui.components.Header
import br.com.brunocarvalhs.costs.costs_list.ui.components.ListCosts
import br.com.brunocarvalhs.domain.entities.CostEntities

@Composable
fun CostsScreen(navController: NavController, viewModel: CostsViewModel) {
    val uiState = viewModel.state.collectAsState()

    CostsContent(
        uiState = uiState.value,
        session = viewModel.header,
        onProfile = { viewModel.navigateToProfile(navController) },
        onAdd = { viewModel.navigateToAddCosts(navController) },
        onGroups = { viewModel.navigateToGroups(navController) },
        onExtracts = { viewModel.navigateToExtracts(navController) },
        onCalculation = { viewModel.navigateToCalculation(navController) },
        onCheckList = { viewModel.navigateToCheckList(navController) },
        onCost = { viewModel.onClick(it, navController) },
        onLongCost = { viewModel.onLongClick(it, navController) },
        onCostLeft = { viewModel.onSwipeLeft(it, navController) },
        onCostRight = { viewModel.onSwipeRight(it, navController) },
    )
}

@Composable
private fun CostsContent(
    onProfile: () -> Unit,
    session: CostsViewModel.Header? = null,
    uiState: CostsViewState,
    onAdd: () -> Unit = {},
    onGroups: (() -> Unit)? = null,
    onExtracts: (() -> Unit)? = null,
    onCalculation: (() -> Unit)? = null,
    onCheckList: (() -> Unit)? = null,
    onCost: ((CostEntities) -> Unit)? = null,
    onLongCost: ((CostEntities) -> Unit)? = null,
    onCostLeft: ((CostEntities) -> Unit)? = null,
    onCostRight: ((CostEntities) -> Unit)? = null,
) {
    Scaffold(
        topBar = {
            Header(
                name = session?.name.orEmpty(), session?.photoUrl
            ) { onProfile.invoke() }
        },
        bottomBar = {
            BottomNavigation(
                onClickFloatingAction = onAdd,
                onHome = onGroups,
                onExtracts = onExtracts,
                onCalculation = onCalculation,
                onCheckList = onCheckList
            )
        },
    ) {
        when (uiState) {
            is CostsViewState.Error -> {
                Column {
                    Text(text = uiState.message.orEmpty())
                    Button(onClick = { }) { Text(text = "Recarregar") }
                }
            }

            CostsViewState.Loading -> {
                Column {
                    CircularProgressIndicator()
                }
            }

            is CostsViewState.Success -> {
                ListCosts(
                    list = uiState.list,
                    onClick = { cost -> onCost?.invoke(cost) },
                    onLongClick = { cost -> onLongCost?.invoke(cost) },
                    onLeft = { cost -> onCostLeft?.invoke(cost) },
                    onRight = { cost -> onCostRight?.invoke(cost) },
                    modifier = Modifier.padding(it)
                )
            }
        }

    }
}

@Composable
@Preview
private fun PreviewCostsContent() {
    PagueiTheme {
        CostsContent(
            onProfile = {}, uiState = CostsViewState.Loading
        )
    }
}