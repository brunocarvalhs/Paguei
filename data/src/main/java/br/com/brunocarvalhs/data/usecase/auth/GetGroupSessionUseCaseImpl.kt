package br.com.brunocarvalhs.data.usecase.auth

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.services.SessionManager
import br.com.brunocarvalhs.domain.usecase.auth.GetGroupSessionUseCase
import javax.inject.Inject

class GetGroupSessionUseCaseImpl @Inject constructor(
    private val sessionManager: SessionManager,
) : GetGroupSessionUseCase {
    override suspend fun invoke(group: GroupEntities?): Result<Unit> {
        return try {
            sessionManager.sessionGroup(group)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}