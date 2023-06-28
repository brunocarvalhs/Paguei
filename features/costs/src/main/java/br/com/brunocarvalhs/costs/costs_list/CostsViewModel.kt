package br.com.brunocarvalhs.costs.costs_list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.cost.DeleteCostUseCase
import br.com.brunocarvalhs.domain.usecase.cost.FetchCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val useCase: FetchCostsUseCase,
    private val deleteCostUseCase: DeleteCostUseCase,
    sessionManager: SessionManager
) : BaseViewModel<CostsViewState>() {

    val header: Header = Header(
        name = sessionManager.getGroup()?.name ?: sessionManager.getUser()?.firstName(),
        photoUrl = sessionManager.getUser()?.photoUrl,
        initials = sessionManager.getUser()?.initialsName(),
        isGroup = sessionManager.isGroupSession()
    )

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            mutableState.value = CostsViewState.Loading
            useCase.invoke().onSuccess {
                listCosts = it.toMutableList()
                mutableState.value = CostsViewState.Success(listCosts)
            }.onFailure { error -> mutableState.value = CostsViewState.Error(error.message) }
        }
    }

    fun deleteCost(cost: CostEntities, callback: () -> Unit) {
        viewModelScope.launch {
            deleteCostUseCase.invoke(cost)
                .onSuccess { callback.invoke() }
                .onFailure { error -> mutableState.value = CostsViewState.Error(error.message) }
        }
    }

    data class Header(
        val name: String?,
        val photoUrl: String? = null,
        val initials: String?,
        val isGroup: Boolean = false
    )
}
