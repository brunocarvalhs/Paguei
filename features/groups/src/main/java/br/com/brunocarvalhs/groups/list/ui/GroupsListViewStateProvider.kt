package br.com.brunocarvalhs.groups.list.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import br.com.brunocarvalhs.domain.entities.GroupEntities
import br.com.brunocarvalhs.domain.entities.UserEntities
import br.com.brunocarvalhs.groups.list.GroupsListViewState

class GroupsListViewStateProvider : PreviewParameterProvider<GroupsListViewState> {

    override val values: Sequence<GroupsListViewState>
        get() = sequenceOf(
            GroupsListViewState.Loading,
            GroupsListViewState.Error("message error"),
            GroupsListViewState.Success(list = listOf(object : GroupEntities {
                override val id: String
                    get() = "faker.random.nextUUID()"
                override val name: String
                    get() = "ABC"
                override val members: List<String>
                    get() = List(100) { "faker.random.nextUUID()" }

                override fun toMap(): Map<String?, Any?> = emptyMap()

                override fun toJson(): String = ""

                override fun copyWith(name: String?, members: List<String>): GroupEntities = this

            }), user = object : UserEntities {
                override val id: String
                    get() = "faker.random.nextUUID()"
                override val name: String
                    get() = "Jose Abrel"
                override val photoUrl: String
                    get() = ""
                override val email: String
                    get() = "email@email.com"
                override val salary: String
                    get() = "1000.00"
                override val token: String
                    get() = ""

                override fun toMap(): Map<String?, Any?> = emptyMap()
                override fun toJson(): String = ""
                override fun firstName(): String = ""
                override fun lastName(): String = ""
                override fun initialsName(): String = ""
                override fun formatSalary(): String = ""

                override fun copyWith(
                    name: String?, photoUrl: String?, email: String?, salary: String?
                ): UserEntities = this

            }),
        )

}