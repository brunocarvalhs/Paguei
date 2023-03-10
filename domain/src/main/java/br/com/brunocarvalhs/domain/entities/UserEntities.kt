package br.com.brunocarvalhs.domain.entities

import java.io.Serializable

interface UserEntities : Serializable {
    val id: String
    val name: String?
    val photoUrl: String?
    val email: String?
    val salary: String?
    val token: String?

    fun toMap(): Map<String?, Any?>
    fun toJson(): String

    fun firstName(): String?
    fun lastName(): String?

    fun initialsName(): String?
    fun formatSalary(): String

    companion object {
        const val COLLECTION = "users"

        const val ID = "id"
        const val NAME = "name"
        const val PHOTO_URL = "photoUrl"
        const val EMAIL = "email"
        const val SALARY = "salary"
        const val TOKEN = "token"
    }
}