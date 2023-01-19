package br.com.brunocarvalhs.data.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class HomesModelTest {

    @Test
    fun testHomesModel() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val members = listOf("member1", "member2")

        // Act
        val homesModel = HomesModel(id, name, members)

        // Assert
        assertEquals(id, homesModel.id)
        assertEquals(name, homesModel.name)
        assertEquals(members, homesModel.members)

        val map = homesModel.toMap()
        assertEquals(id, map[HomesModel.ID])
        assertEquals(name, map[HomesModel.NAME])
        assertEquals(members, map[HomesModel.MEMBERS])

        val json = homesModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, HomesModel::class.java)
        assertEquals(homesModel, fromJson)
    }
}
