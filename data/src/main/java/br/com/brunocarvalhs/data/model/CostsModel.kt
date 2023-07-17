package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.BAR_CODE
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.DATE_PAYMENT
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.DATE_REFERENCE_MONTH
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.ID
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.NAME
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.PAYMENT_VOUCHER
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.PROMPT
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.TYPE_COST
import br.com.brunocarvalhs.domain.entities.CostEntities.Companion.VALUE
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
    @SerializedName(DATE_REFERENCE_MONTH) override val dateReferenceMonth: String? = null,
    @SerializedName(TYPE_COST) override val type: String? = null,
) : CostEntities {
    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
    override fun formatValue(): String {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault())
        numberFormat.maximumFractionDigits = 2
        numberFormat.minimumFractionDigits = 2
        return numberFormat.format((this.value)?.toDouble())
    }

    override fun copyWith(
        name: String?,
        prompt: String?,
        value: String?,
        barCode: String?,
        paymentVoucher: String?,
        datePayment: String?,
        dateReferenceMonth: String?
    ): CostEntities {
        return this.copy(
            id = id,
            name = prompt,
            prompt = value,
            value = barCode,
            barCode = paymentVoucher,
            paymentVoucher = datePayment,
            datePayment = dateReferenceMonth
        )
    }

    companion object {
        fun fromJson(value: String): CostEntities = Gson().fromJson(value, CostsModel::class.java)
    }
}
