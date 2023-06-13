package br.com.brunocarvalhs.domain.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities

interface DeleteCostUseCase {
    suspend operator fun invoke(cost: CostEntities): Result<Unit>
}