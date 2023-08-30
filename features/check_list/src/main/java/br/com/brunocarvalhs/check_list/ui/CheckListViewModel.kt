package br.com.brunocarvalhs.check_list.ui

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import br.com.brunocarvalhs.commons.BaseComposeViewModel
import br.com.brunocarvalhs.data.navigation.Navigation
import br.com.brunocarvalhs.domain.usecase.check_list.CheckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkListUseCase: CheckListUseCase,
    private val navigation: Navigation,
) : BaseComposeViewModel<CheckListViewState>(CheckListViewState.Loading) {
    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = CheckListViewState.Loading
            checkListUseCase.invoke().onSuccess {
                mutableState.value = CheckListViewState.Success(it)
            }.onFailure {
                mutableState.value = CheckListViewState.Error(it.message)
            }
        }
    }

    fun onSelect(navController: NavController, name: String?) {
        val action = navigation.navigateToBilletRegistrationForm()
        navController.navigate(action)
    }
}