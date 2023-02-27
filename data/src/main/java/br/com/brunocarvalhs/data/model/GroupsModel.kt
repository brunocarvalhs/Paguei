package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.GroupEntities.Companion.ID
import br.com.brunocarvalhs.domain.entities.GroupEntities.Companion.NAME
import br.com.brunocarvalhs.domain.entities.GroupEntities.Companion.MEMBERS
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

data class GroupsModel(
    @SerializedName(ID) override val id: String = UUID.randomUUID().toString(),
    @SerializedName(NAME) override val name: String? = null,
    @SerializedName(MEMBERS) override val members: List<String> = emptyList()
) : GroupEntities {
    override fun toMap(): Map<String?, Any?> =
        Gson().fromJson(this.toJson(), HashMap<String?, Any?>().javaClass)

    override fun toJson(): String = Gson().toJson(this)
}