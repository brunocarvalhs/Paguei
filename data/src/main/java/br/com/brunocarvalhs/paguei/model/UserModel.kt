package br.com.brunocarvalhs.paguei.model

import android.os.Parcelable
import br.com.brunocarvalhs.paguei.domain.entities.UserEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(PHOTO_URL) override val photoUrl: String? = null,
    @SerializedName(EMAIL) override val email: String? = null,
    @SerializedName(PHONE_NUMBER) override val phoneNumber: String? = null,
) : UserEntities, Parcelable {
    companion object {

        const val COLLECTION = "users"

        const val ID = "id"
        const val NAME = "name"
        const val PHOTO_URL = "photo_url"
        const val EMAIL = "email"
        const val PHONE_NUMBER = "phone_number"
    }

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)

    override fun fistName(): String? = name?.split(" ")?.first()

    override fun lastName(): String? = name?.split(" ")?.last()
    override fun initialsName(): String = fistName()?.substring(0, 1) + lastName()?.substring(0, 1)
}
