package br.com.brunocarvalhs.data.usecase.cost

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.repositories.CostsRepository
import br.com.brunocarvalhs.domain.usecase.cost.FetchExtractsCostsUseCase
import javax.inject.Inject

class FetchExtractsCostsUseCaseImpl @Inject constructor(
    private val repository: CostsRepository,
) : FetchExtractsCostsUseCase {
    override suspend fun invoke(): Result<List<CostEntities>> {
        return try {
            val result =
                repository.list().filter { !it.paymentVoucher.isNullOrEmpty() }.toMutableList()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}