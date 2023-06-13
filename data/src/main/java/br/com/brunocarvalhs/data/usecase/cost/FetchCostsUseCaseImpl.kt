package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.FetchCostsUseCase
import javax.inject.Inject

class FetchCostsUseCaseImpl @Inject constructor(private val repository: CostsRepository) :
    FetchCostsUseCase {
    override suspend fun invoke(): Result<List<CostEntities>> {
        return try {
            val costs = repository.list().filter { it.paymentVoucher.isNullOrEmpty() }
            Result.success(costs)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}