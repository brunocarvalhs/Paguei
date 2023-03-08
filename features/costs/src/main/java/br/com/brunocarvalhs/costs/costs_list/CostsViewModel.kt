package br.com.brunocarvalhs.costs.costs_list

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.services.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    private val repository: CostsRepository,
    sessionManager: SessionManager
) : BaseViewModel<CostsViewState>() {

    val header: Header = Header(
        name = sessionManager.getGroup()?.name ?: sessionManager.getUser()?.fistName(),
        photoUrl = sessionManager.getUser()?.photoUrl,
        initials = sessionManager.getUser()?.initialsName(),
        isGroup = sessionManager.isGroupSession()
    )

//    val user: UserEntities? = sessionManager.getUser()

//    var group: GroupEntities? = sessionManager.getGroup()

    private var listCosts = mutableListOf<CostEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CostsViewState.Loading
                listCosts =
                    repository.list().filter { it.paymentVoucher.isNullOrEmpty() }.toMutableList()
                mutableState.value = CostsViewState.Success(listCosts)
            } catch (error: Exception) {
                mutableState.value = CostsViewState.Error(error.message)
            }
        }
    }

    data class Header(
        val name: String?,
        val photoUrl: String? = null,
        val initials: String?,
        val isGroup: Boolean = false
    )
}
