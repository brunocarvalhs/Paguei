package br.com.brunocarvalhs.domain.entities

interface UserEntities {
    val id: String
    val name: String?
    val photoUrl: String?
    val email: String?
    val phoneNumber: String?

    fun toMap(): Map<String?, Any?>
    fun toJson(): String

    fun fistName(): String?
    fun lastName(): String?

    fun initialsName(): String?
}