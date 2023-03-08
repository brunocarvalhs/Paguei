package br.com.brunocarvalhs.domain.entities

import java.io.Serializable

interface CostEntities : Serializable {
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

    companion object {

        const val COLLECTION = "costs"

        const val ID = "id"
        const val NAME = "name"
        const val PROMPT = "prompt"
        const val VALUE = "value"
        const val BAR_CODE = "barCode"
        const val PAYMENT_VOUCHER = "paymentVoucher"
        const val DATE_PAYMENT = "datePayment"
    }
}