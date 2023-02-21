package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.CostsEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.util.*

data class CostsModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PROMPT) override val prompt: String? = null,
    @SerializedName(VALUE) override val value: String? = null,
    @SerializedName(BAR_CODE) override val barCode: String? = null,
    @SerializedName(PAYMENT_VOUCHER) override val paymentVoucher: String? = null,
    @SerializedName(TYPE) override val type: String? = null,
    @SerializedName(DATE_PAYMENT) override val datePayment: String? = null,
    @SerializedName(TAGS) override val tags: List<String> = emptyList(),
) : CostsEntities {
    companion object {

        const val COLLECTION = "costs"

        const val ID = "id"
        const val NAME = "name"
        const val PROMPT = "prompt"
        const val VALUE = "value"
        const val TYPE = "type"
        const val TAGS = "tags"
        const val BAR_CODE = "barCode"
        const val PAYMENT_VOUCHER = "paymentVoucher"
        const val DATE_PAYMENT = "datePayment"

        const val FORMAT_VALUE = "#,###.00"
    }

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
    override fun formatValue(): String =
        DecimalFormat(FORMAT_VALUE).format((this.value ?: "0").toDouble())
}
