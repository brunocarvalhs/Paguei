package br.com.brunocarvalhs.data.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.report.CalculateExpensesByCategoryUseCase
import javax.inject.Inject

class CalculateExpensesByCategoryUseCaseImpl @Inject constructor() : CalculateExpensesByCategoryUseCase {
    override suspend fun invoke(costs: List<CostEntities>): Map<String, Double> {
        val costItCategory = mutableMapOf<String, Double>()
        for (cost in costs) {
            val category = extractCostCategory(cost)
            if (costItCategory.containsKey(category)) {
                costItCategory[category] =
                    costItCategory[category]?.plus((cost.value?.toDouble() ?: (0).toDouble()))
                        ?: (0).toDouble()
            } else {
                costItCategory[category] = cost.value?.toDouble() ?: (0).toDouble()
            }
        }
        return costItCategory
    }

    private fun extractCostCategory(cost: CostEntities): String {
        return cost.name.orEmpty()
    }
}