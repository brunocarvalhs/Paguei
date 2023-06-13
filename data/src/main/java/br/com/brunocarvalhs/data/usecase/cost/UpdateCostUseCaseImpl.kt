package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.UpdateCostUseCase
import javax.inject.Inject

class UpdateCostUseCaseImpl @Inject constructor(
    private val repository: CostsRepository
) : UpdateCostUseCase {
    override suspend fun invoke(cost: CostEntities): Result<CostEntities> {
        return try {
            val result = repository.update(cost)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}