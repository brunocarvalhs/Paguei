package br.com.brunocarvalhs.data.model

import android.os.Parcelable
import br.com.brunocarvalhs.payflow.domain.entities.CostsEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CostsModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PROMPT) override val prompt: String? = null,
    @SerializedName(VALUE) override val value: Double? = null,
    @SerializedName(BAR_CODE) override val barCode: String? = null,
) : CostsEntities, Parcelable {
    companion object {

        const val COLLECTION = "costs"

        const val ID = "id"
        const val NAME = "name"
        const val PROMPT = "prompt"
        const val VALUE = "value"
        const val BAR_CODE = "bar_code"
    }

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
}
