package br.com.brunocarvalhs.costs.ui.selected_cost

import androidx.lifecycle.SavedStateHandle
import br.com.brunocarvalhs.commons.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CostsSelectedViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CostsSelectedViewState>() {

    val cost = CostsSelectedDialogFragmentArgs.fromSavedStateHandle(savedStateHandle).costs

}