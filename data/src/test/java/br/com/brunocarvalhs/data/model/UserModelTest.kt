package br.com.brunocarvalhs.data.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class UserModelTest {

    @Test
    fun testUserModel() {
        // Arrange
        val id = "123456"
        val name = "John Doe"
        val photoUrl = "https://example.com/john-doe.jpg"
        val email = "john.doe@example.com"
        val phoneNumber = "555-555-5555"

        // Act
        val userModel = UserModel(id, name, photoUrl, email, phoneNumber)

        // Assert
        assertEquals(id, userModel.id)
        assertEquals(name, userModel.name)
        assertEquals(photoUrl, userModel.photoUrl)
        assertEquals(email, userModel.email)
        assertEquals(phoneNumber, userModel.phoneNumber)

        val map = userModel.toMap()
        assertEquals(id, map[UserModel.ID])
        assertEquals(name, map[UserModel.NAME])
        assertEquals(photoUrl, map[UserModel.PHOTO_URL])
        assertEquals(email, map[UserModel.EMAIL])
        assertEquals(phoneNumber, map[UserModel.PHONE_NUMBER])

        val json = userModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, UserModel::class.java)
        assertEquals(userModel, fromJson)

        assertEquals("John", userModel.fistName())
        assertEquals("Doe", userModel.lastName())
        assertEquals("JD", userModel.initialsName())
    }
}
