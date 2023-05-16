package br.com.brunocarvalhs.data.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.report.CalculatePaymentPromptnessUseCase
import java.util.Date
import javax.inject.Inject

class CalculatePaymentPromptnessUseCaseImpl @Inject constructor() : CalculatePaymentPromptnessUseCase {
    override suspend fun invoke(costs: List<CostEntities>, dueDate: Date): Double {
        val transacoesAntes = costs.filter { Date(it.prompt) < dueDate }
        val transacoesDepois = costs.filter { Date(it.prompt) > dueDate }
        return transacoesAntes.size.toDouble() / (transacoesAntes.size + transacoesDepois.size)
    }
}