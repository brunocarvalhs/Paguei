package br.com.brunocarvalhs.domain.usecase.report

import br.com.brunocarvalhs.domain.entities.CostEntities

interface PlotTrendGraphUseCase {
    suspend operator fun invoke(costs: List<CostEntities>): Pair<List<String>, List<Any>>
}