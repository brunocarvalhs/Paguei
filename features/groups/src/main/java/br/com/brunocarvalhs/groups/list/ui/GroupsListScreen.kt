package br.com.brunocarvalhs.groups.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.groups.list.GroupsListViewModel
import br.com.brunocarvalhs.groups.list.GroupsListViewState
import br.com.brunocarvalhs.groups.list.ui.components.GroupItem
import br.com.brunocarvalhs.groups.list.ui.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsListScreen(navController: NavController, viewModel: GroupsListViewModel) {
    GroupsListContent(
        uiState = GroupsListViewState.Loading,
        onSelect = viewModel::selected,
        onRight = { },
        onLeft = { },
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GroupsListContent(
    uiState: GroupsListViewState,
    onSelect: (select: GroupEntities?) -> Unit,
    onLeft: (select: GroupEntities) -> Unit,
    onRight: (select: GroupEntities) -> Unit,
    content: @Composable (BottomSheetScaffoldState) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(scaffoldState = scaffoldState,
        sheetPeekHeight = 500.dp,
        sheetSwipeEnabled = true,
        sheetContent = {
            when (uiState) {
                GroupsListViewState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is GroupsListViewState.Success -> LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        UserItem(user = uiState.user, onClick = { onSelect.invoke(null) })
                    }
                    items(uiState.list) { groupEntities ->
                        GroupItem(
                            group = groupEntities,
                            onClick = { onSelect.invoke(it) },
                            onLeft = onLeft,
                            onRight = onRight
                        )
                    }
                }

                is GroupsListViewState.Error -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { Text(text = uiState.error.orEmpty()) }
            }
        }) {
        content(scaffoldState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun PreviewGroupsListScreen(
    @PreviewParameter(GroupsListViewStateProvider::class) state: GroupsListViewState
) {
    PagueiTheme {
        GroupsListContent(
            uiState = state,
            onSelect = {},
            onLeft = {},
            onRight = {},
        ) {

        }
    }
}