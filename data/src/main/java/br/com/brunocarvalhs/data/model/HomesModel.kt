package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.HomesEntities
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

data class HomesModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(MEMBERS) override val members: List<String> = emptyList()
) : HomesEntities {
    companion object {

        const val COLLECTION = "homes"

        const val ID = "id"
        const val NAME = "name"
        const val MEMBERS = "members"
    }

    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
}