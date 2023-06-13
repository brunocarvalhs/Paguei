package br.com.brunocarvalhs.data.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.report.CalculateExpenseFrequencyUseCase
import javax.inject.Inject

class CalculateExpenseFrequencyUseCaseImpl @Inject constructor() :
    CalculateExpenseFrequencyUseCase {
    override suspend fun invoke(costs: List<CostEntities>): Map<String, Int> {
        val frequency = mutableMapOf<String, Int>()
        for (cost in costs) {
            val category = extractCostCategory(cost)
            if (frequency.containsKey(category)) {
                frequency[category] = frequency[category]!! + 1
            } else {
                frequency[category] = 1
            }
        }
        return frequency
    }

    private fun extractCostCategory(costs: CostEntities): String {
        return costs.name.orEmpty()
    }
}