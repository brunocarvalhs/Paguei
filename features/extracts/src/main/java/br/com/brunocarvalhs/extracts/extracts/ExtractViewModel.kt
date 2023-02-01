package br.com.brunocarvalhs.paguei.features.costs.extracts

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.extracts.extracts.ExtractViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtractViewModel @Inject constructor(
    private val repository: CostsRepository,
) : BaseViewModel<ExtractViewState>() {

    private var listCosts = mutableListOf<CostsEntities>()
        set(value) {
            if (!listCosts.containsAll(value)) {
                field = value
            }
        }

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = ExtractViewState.Loading
                listCosts =
                    repository.list().filter { !it.paymentVoucher.isNullOrEmpty() }.toMutableList()
                mutableState.value = ExtractViewState.Success(listCosts)
            } catch (error: Exception) {
                mutableState.value = ExtractViewState.Error(error.message)
            }
        }
    }
}
