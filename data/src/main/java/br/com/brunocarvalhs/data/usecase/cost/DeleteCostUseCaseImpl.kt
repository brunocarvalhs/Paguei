package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.DeleteCostUseCase
import javax.inject.Inject

class DeleteCostUseCaseImpl @Inject constructor(
    private val repository: CostsRepository
) : DeleteCostUseCase {
    override suspend fun invoke(cost: CostEntities): Result<Unit> {
        return try {
            repository.delete(cost)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}