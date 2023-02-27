package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.CostsEntities
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.ID
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.NAME
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.PROMPT
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.VALUE
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.BAR_CODE
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.PAYMENT_VOUCHER
import br.com.brunocarvalhs.domain.entities.CostsEntities.Companion.DATE_PAYMENT
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

data class CostsModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PROMPT) override val prompt: String? = null,
    @SerializedName(VALUE) override val value: String? = (0).toString(),
    @SerializedName(BAR_CODE) override val barCode: String? = null,
    @SerializedName(PAYMENT_VOUCHER) override val paymentVoucher: String? = null,
    @SerializedName(DATE_PAYMENT) override val datePayment: String? = null,
) : CostsEntities {
    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
    override fun formatValue(): String {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format((this.value)?.toDouble())
    }
}
