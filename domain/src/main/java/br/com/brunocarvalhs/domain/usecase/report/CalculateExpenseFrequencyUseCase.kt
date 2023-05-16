package br.com.brunocarvalhs.domain.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities

interface CalculateExpenseFrequencyUseCase {
    suspend operator fun invoke(costs: List<CostEntities>): Map<String, Int>
}