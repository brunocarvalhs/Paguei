package br.com.brunocarvalhs.domain.services

interface UpdateVersionService {

    fun <T : Any> updateApplication(
        activity: T,
        type: Type = Type.FLEXIBLE,
        function: (Void) -> Unit
    )

    enum class Type {
        IMMEDIATE,
        FLEXIBLE
    }
}