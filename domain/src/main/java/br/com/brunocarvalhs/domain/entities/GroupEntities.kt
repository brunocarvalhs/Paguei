package br.com.brunocarvalhs.domain.entities

import java.io.Serializable

interface GroupEntities : Serializable {
    val id: String
    val name: String?
    val members: List<String>

    fun toMap(): Map<String?, Any?>
    fun toJson(): String

    companion object {

        const val COLLECTION = "groups"

        const val ID = "id"
        const val NAME = "name"
        const val MEMBERS = "members"
    }
}