package br.com.brunocarvalhs.domain.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities

interface UpdateCostUseCase {
    suspend operator fun invoke(cost: CostEntities): Result<CostEntities>
}