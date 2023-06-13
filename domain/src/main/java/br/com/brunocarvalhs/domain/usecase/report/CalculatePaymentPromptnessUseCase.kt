package br.com.brunocarvalhs.domain.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities
import java.util.Date

interface CalculatePaymentPromptnessUseCase {
    suspend operator fun invoke(costs: List<CostEntities>, dueDate: Date): Double
}