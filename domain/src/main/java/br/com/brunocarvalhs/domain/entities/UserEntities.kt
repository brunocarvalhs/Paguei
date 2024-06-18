package br.com.brunocarvalhs.domain.entities

import java.io.Serializable
import java.util.HashMap

interface UserEntities : Serializable {
    val id: String
    val name: String?
    val photoUrl: String?
    val email: String?
    val salary: String?
    val token: String?

    fun toMap(): Map<String, Any>
    fun toJson(): String

    fun firstName(): String?
    fun lastName(): String?

    fun initialsName(): String?
    fun formatSalary(): String

    fun copyWith(
        name: String? = this.name,
        photoUrl: String? = this.photoUrl,
        email: String? = this.email,
        salary: String? = this.salary,
    ): UserEntities

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