package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.CostsEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

data class CostsModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PROMPT) override val prompt: String? = null,
    @SerializedName(VALUE) override val value: String? = null,
    @SerializedName(BAR_CODE) override val barCode: String? = null,
    @SerializedName(PAYMENT_VOUCHER) override val paymentVoucher: String? = null,
    @SerializedName(DATE_PAYMENT) override val datePayment: String? = null,
) : CostsEntities {
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

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
    override fun formatValue(): String =
        NumberFormat.getInstance(Locale.getDefault()).format((this.value ?: "0").toDouble())
}
