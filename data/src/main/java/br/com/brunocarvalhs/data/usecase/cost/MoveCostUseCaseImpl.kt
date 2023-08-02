package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.MoveCostUseCase
import javax.inject.Inject

class MoveCostUseCaseImpl @Inject constructor(private val repository: CostsRepository) :
    MoveCostUseCase {
    override suspend operator fun invoke(
        cost: CostEntities,
        targetCollection: String,
        targetId: String
    ): Result<Unit> {
        return try {
            repository.moveDocumentToCollection(cost, targetCollection, targetId)
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}