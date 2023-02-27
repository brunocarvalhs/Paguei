package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.UserEntities
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
        val token =
            "88u/JfvBkQOoLN4VX7NVCqOZetvH-9NLJgWWmm3sA=wr?QWQlg35QndQEEoIZCUVNKw20lGjQ-oO02?kyjlU80I/=tJgbf=v?ou3nMB8aJlA3p6PRxwG79VOWcNkoBUxNTqY3nG98SIHyFYOZr1IKqlFlGsIB3Fj3MW4r4UK/9Ugle?4wXgF-4?sugk2g9!F=z8nwANosTfi3hmTC!5NrpWdyrQIi!3LGm0gUp!88ByGKV5DX52f2xRE1gbGfuxK"
        val salary = "12345.00"

        // Act
        val userModel = UserModel(
            id = id, name = name,
            photoUrl = photoUrl,
            email = email,
            salary = salary,
            token = token
        )

        // Assert
        assertEquals(id, userModel.id)
        assertEquals(name, userModel.name)
        assertEquals(photoUrl, userModel.photoUrl)
        assertEquals(email, userModel.email)
        assertEquals(token, userModel.token)
        assertEquals(salary, userModel.salary)

        val map = userModel.toMap()
        assertEquals(id, map[UserEntities.ID])
        assertEquals(name, map[UserEntities.NAME])
        assertEquals(photoUrl, map[UserEntities.PHOTO_URL])
        assertEquals(email, map[UserEntities.EMAIL])
        assertEquals(token, map[UserEntities.TOKEN])
        assertEquals(salary, map[UserEntities.SALARY])

        val json = userModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, UserModel::class.java)
        assertEquals(userModel, fromJson)

        assertEquals("John", userModel.fistName())
        assertEquals("Doe", userModel.lastName())
        assertEquals("JD", userModel.initialsName())
    }
}
