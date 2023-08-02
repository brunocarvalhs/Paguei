package br.com.brunocarvalhs.data.usecase.report

import android.icu.text.SimpleDateFormat
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.report.CalculatePaymentPromptnessUseCase
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CalculatePaymentPromptnessUseCaseImpl @Inject constructor() : CalculatePaymentPromptnessUseCase {
    override suspend fun invoke(costs: List<CostEntities>, dueDate: Date): Double {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val transacoesAntes = costs.filter { dateFormat.parse(it.prompt) < dueDate }
        val transacoesDepois = costs.filter { dateFormat.parse(it.prompt) > dueDate }
        return transacoesAntes.size.toDouble() / (transacoesAntes.size + transacoesDepois.size)
    }
}