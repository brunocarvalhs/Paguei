package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.GroupEntities
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class GroupModelTest {

    @Test
    fun testGroupsModel() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val members = listOf("member1", "member2")

        // Act
        val groupsModel = GroupsModel(id = id, name = name, members = members)

        // Assert
        assertEquals(id, groupsModel.id)
        assertEquals(name, groupsModel.name)
        assertEquals(members, groupsModel.members)

        val map = groupsModel.toMap()
        assertEquals(id, map[GroupEntities.ID])
        assertEquals(name, map[GroupEntities.NAME])
        assertEquals(members, map[GroupEntities.MEMBERS])

        val json = groupsModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, GroupsModel::class.java)
        assertEquals(groupsModel, fromJson)
    }
}
