package br.com.brunocarvalhs.data.model

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class CostsModelTest {

    @Test
    fun testCostsModel() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val prompt = "Test Prompt"
        val value = "1234.56"
        val barCode = "1234567890"
        val paymentVoucher = "VOUCHER123"

        // Act
        val costsModel = CostsModel(id, name, prompt, value, barCode, paymentVoucher)

        // Assert
        assertEquals(id, costsModel.id)
        assertEquals(name, costsModel.name)
        assertEquals(prompt, costsModel.prompt)
        assertEquals(value, costsModel.value)
        assertEquals(barCode, costsModel.barCode)
        assertEquals(paymentVoucher, costsModel.paymentVoucher)

        val map = costsModel.toMap()
        assertEquals(id, map[CostsModel.ID])
        assertEquals(name, map[CostsModel.NAME])
        assertEquals(prompt, map[CostsModel.PROMPT])
        assertEquals(value, map[CostsModel.VALUE])
        assertEquals(barCode, map[CostsModel.BAR_CODE])
        assertEquals(paymentVoucher, map[CostsModel.PAYMENT_VOUCHER])

        val json = costsModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, CostsModel::class.java)
        assertEquals(costsModel, fromJson)
    }
}
