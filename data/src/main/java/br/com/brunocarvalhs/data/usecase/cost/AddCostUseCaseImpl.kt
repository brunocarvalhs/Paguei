package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.AddCostUseCase
import javax.inject.Inject

class AddCostUseCaseImpl @Inject constructor(private val repository: CostsRepository) :
    AddCostUseCase {
    override suspend fun invoke(cost: CostEntities): Result<Unit> {
        return try {
            repository.add(cost)
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}