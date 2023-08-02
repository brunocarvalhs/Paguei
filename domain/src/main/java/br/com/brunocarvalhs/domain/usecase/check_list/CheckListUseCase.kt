package br.com.brunocarvalhs.domain.usecase.check_list

interface CheckListUseCase {
    suspend operator fun invoke(): Result<HashMap<String, Map<String?, Boolean>>>
}