package br.com.brunocarvalhs.check_list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.usecase.check_list.CheckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkListUseCase: CheckListUseCase
) : BaseViewModel<CheckListViewState>() {
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
}