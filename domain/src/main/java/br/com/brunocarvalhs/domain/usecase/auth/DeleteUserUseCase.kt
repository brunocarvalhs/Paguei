package br.com.brunocarvalhs.domain.usecase.auth

interface DeleteUserUseCase {
    suspend operator fun invoke() : Result<Unit>
}