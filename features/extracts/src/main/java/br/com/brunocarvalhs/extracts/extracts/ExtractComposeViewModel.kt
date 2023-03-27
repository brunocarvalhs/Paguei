package br.com.brunocarvalhs.extracts.extracts

import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModelCompose
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtractComposeViewModel @Inject constructor(
    private val repository: CostsRepository,
) : BaseViewModelCompose<ExtractViewState>(ExtractViewState.Loading) {

    private var listCosts = mutableListOf<CostEntities>()
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
