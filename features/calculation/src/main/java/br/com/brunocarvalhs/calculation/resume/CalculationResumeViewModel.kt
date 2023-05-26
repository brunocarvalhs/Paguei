package br.com.brunocarvalhs.calculation.resume

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.moneyToDouble
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.domain.entities.UserEntities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculationResumeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<CalculationResumeViewState>() {

    private val members =
        CalculationResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).members.map {
            UserModel.fromJson(it)
        }

    private val totalSalary =
        CalculationResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).totalSalary.replace(
            ",",
            "."
        ).toDouble()

    private val totalCosts =
        CalculationResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).totalCosts.toDouble()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CalculationResumeViewState.Loading
                val percentagesToMembers = hashMapOf<UserEntities, Double>()
                percentagesToMembers.putAll(members.associate { calculate(it) })
                mutableState.value = CalculationResumeViewState.Success(percentagesToMembers)
            } catch (e: Exception) {
                mutableState.value = CalculationResumeViewState.Error(e.message)
            }
        }
    }

    private fun calculate(user: UserEntities): Pair<UserEntities, Double> {
        val userSalary = user.salary?.toDouble() ?: totalSalary
        val percentage = (userSalary / totalSalary) * 100

        return Pair(user, percentage)
    }
}