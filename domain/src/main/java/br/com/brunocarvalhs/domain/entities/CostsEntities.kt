package br.com.brunocarvalhs.domain.entities

import java.io.Serializable

interface CostsEntities : Serializable {
    val id: String
    val name: String?
    val prompt: String?
    val value: String?
    val barCode: String?
    val paymentVoucher: String?
    val datePayment: String?

    fun toMap(): Map<String?, Any?>
    fun toJson(): String
    fun formatValue(): String
}