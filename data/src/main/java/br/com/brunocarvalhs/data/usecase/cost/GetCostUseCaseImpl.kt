package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.usecase.cost.GetCostUseCase

class GetCostUseCaseImpl constructor() : GetCostUseCase {
    override suspend fun invoke(cost: CostEntities): Result<CostEntities> {
        TODO("Not yet implemented")
    }
}