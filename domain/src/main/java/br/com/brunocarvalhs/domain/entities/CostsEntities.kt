package br.com.brunocarvalhs.domain.entities

interface CostsEntities {
    val id: String
    val name: String?
    val prompt: String?
    val value: String?
    val barCode: String?
    val paymentVoucher: String?

    fun toMap(): Map<String?, Any?>
    fun toJson(): String
    fun formatValue(): String
}