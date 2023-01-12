package br.com.brunocarvalhs.payflow.domain.entities

import java.util.*

interface CostsEntities {
    val id: String
    val name: String
    val prompt: Date
    val value: Double
    val barCode: String

    fun toMap(): Map<String?, Any?>
    fun toJson(): String
}