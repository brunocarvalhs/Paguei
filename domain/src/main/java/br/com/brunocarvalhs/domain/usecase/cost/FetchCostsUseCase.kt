package br.com.brunocarvalhs.domain.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities

interface FetchCostsUseCase {
    suspend operator fun invoke(): Result<List<CostEntities>>
}