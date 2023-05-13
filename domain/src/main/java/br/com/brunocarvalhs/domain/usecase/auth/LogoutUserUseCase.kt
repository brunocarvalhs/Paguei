package br.com.brunocarvalhs.domain.usecase.auth

interface LogoutUserUseCase {
    suspend operator fun invoke(): Result<Unit>
}