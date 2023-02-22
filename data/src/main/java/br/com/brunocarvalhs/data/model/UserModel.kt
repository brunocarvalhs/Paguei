package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.UserEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import java.util.*

data class UserModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PHOTO_URL) override val photoUrl: String? = null,
    @SerializedName(EMAIL) override val email: String? = null,
    override val salary: String? = null,
) : UserEntities {
    companion object {

        const val COLLECTION = "users"

        const val ID = "id"
        const val NAME = "name"
        const val PHOTO_URL = "photo_url"
        const val EMAIL = "email"

        const val FORMAT_VALUE = "#.###,00"
    }

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)

    override fun fistName(): String? = name?.split(" ")?.first()

    override fun lastName(): String? = name?.split(" ")?.last()
    override fun initialsName(): String = fistName()?.substring(0, 1) + lastName()?.substring(0, 1)

    override fun formatSalary(): String =
        DecimalFormat(FORMAT_VALUE).format(this.salary.orEmpty().toDouble())
}
