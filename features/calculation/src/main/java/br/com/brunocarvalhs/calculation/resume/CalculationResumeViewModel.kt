package br.com.brunocarvalhs.calculation.resume

import androidx.databinding.ObservableField
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.brunocarvalhs.commons.BaseViewModel
import br.com.brunocarvalhs.commons.utils.formatDecimal
import br.com.brunocarvalhs.commons.utils.moneyReplace
import br.com.brunocarvalhs.commons.utils.orEmpty
import br.com.brunocarvalhs.commons.utils.percentage
import br.com.brunocarvalhs.commons.utils.replateCommaToPoint
import br.com.brunocarvalhs.data.model.UserModel
import br.com.brunocarvalhs.data.utils.moneyFormatted
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
        CalculationResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).totalSalary.replateCommaToPoint()

    val totalCosts =
        ObservableField(CalculationResumeFragmentArgs.fromSavedStateHandle(savedStateHandle).totalCosts)

    val totalSalaryPercent = ObservableField<String>()

    val totalResume = ObservableField<String>()

    fun fetchData() {
        viewModelScope.launch {
            try {
                mutableState.value = CalculationResumeViewState.Loading
                val percentagesToMembers = hashMapOf<UserEntities, Double>()
                percentagesToMembers.putAll(members.associate { calculate(it) })
                calculatePercent(percentagesToMembers)
                calculateResume()
                mutableState.value = CalculationResumeViewState.Success(percentagesToMembers)
            } catch (e: Exception) {
                mutableState.value = CalculationResumeViewState.Error(e.message)
            }
        }
    }

    private fun calculate(user: UserEntities): Pair<UserEntities, Double> {
        val userSalary = (user.salary ?: totalSalary).toDouble()
        val percentage = (userSalary / totalSalary.toDouble()) * 100

        return Pair(user, percentage)
    }

    private fun calculatePercent(user: HashMap<UserEntities, Double>) {
        val result =
            user.map { (user, percent) -> percent.percentage(user.salary?.toDouble().orEmpty()) }
        totalSalaryPercent.set(result.sum().formatDecimal().replateCommaToPoint())
    }

    private fun calculateResume() {
        val salaries: Double = totalSalaryPercent.get()?.toDouble().orEmpty()
        val costs: Double = totalCosts.get()?.moneyReplace()?.toDouble().orEmpty()
        val resultResume = salaries.minus(costs)
        totalResume.set(resultResume.moneyFormatted())
    }
}