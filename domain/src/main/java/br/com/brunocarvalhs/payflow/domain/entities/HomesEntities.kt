package br.com.brunocarvalhs.payflow.domain.entities

interface HomesEntities {
    val id: String
    val name: String?
    val members: List<String>

    fun toMap(): Map<String?, Any?>
    fun toJson(): String
}