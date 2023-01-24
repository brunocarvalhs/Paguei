package br.com.brunocarvalhs.data.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class GroupModelTest {

    @Test
    fun testHomesModel() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val members = listOf("member1", "member2")

        // Act
        val homesModel = GroupsModel(id, name, members)

        // Assert
        assertEquals(id, homesModel.id)
        assertEquals(name, homesModel.name)
        assertEquals(members, homesModel.members)

        val map = homesModel.toMap()
        assertEquals(id, map[GroupsModel.ID])
        assertEquals(name, map[GroupsModel.NAME])
        assertEquals(members, map[GroupsModel.MEMBERS])

        val json = homesModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, GroupsModel::class.java)
        assertEquals(homesModel, fromJson)
    }
}
