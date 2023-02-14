package br.com.brunocarvalhs.domain.entities

import java.io.Serializable

interface UserEntities : Serializable {
    val id: String
    val name: String?
    val photoUrl: String?
    val email: String?
    val salary: String?

    fun toMap(): Map<String?, Any?>
    fun toJson(): String

    fun fistName(): String?
    fun lastName(): String?

    fun initialsName(): String?
    fun formatSalary(): String
}