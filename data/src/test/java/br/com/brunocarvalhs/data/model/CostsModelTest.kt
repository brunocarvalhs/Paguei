package br.com.brunocarvalhs.data.model

import br.com.brunocarvalhs.domain.entities.CostsEntities
import com.google.gson.Gson
import org.junit.Assert.*
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
        val costsModel = CostsModel(
            id = id,
            name = name,
            prompt = prompt,
            value = value,
            barCode = barCode,
            paymentVoucher = paymentVoucher,
        )

        // Assert
        assertEquals(id, costsModel.id)
        assertEquals(name, costsModel.name)
        assertEquals(prompt, costsModel.prompt)
        assertEquals(value, costsModel.value)
        assertEquals(barCode, costsModel.barCode)
        assertEquals(paymentVoucher, costsModel.paymentVoucher)

        val map = costsModel.toMap()
        assertEquals(id, map[CostsEntities.ID])
        assertEquals(name, map[CostsEntities.NAME])
        assertEquals(prompt, map[CostsEntities.PROMPT])
        assertEquals(value, map[CostsEntities.VALUE])
        assertEquals(barCode, map[CostsEntities.BAR_CODE])
        assertEquals(paymentVoucher, map[CostsEntities.PAYMENT_VOUCHER])

        val json = costsModel.toJson()
        val gson = Gson()
        val fromJson = gson.fromJson(json, CostsModel::class.java)
        assertEquals(costsModel, fromJson)
    }

    @Test
    fun `validation of formatted Value`() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val prompt = "Test Prompt"
        val value = "1234.56"
        val barCode = "1234567890"
        val paymentVoucher = "VOUCHER123"

        // Act
        val costsModel = CostsModel(
            id = id,
            name = name,
            prompt = prompt,
            value = value,
            barCode = barCode,
            paymentVoucher = paymentVoucher,
        )

        assertEquals(value, costsModel.value)
        assertEquals("1.234,56", costsModel.formatValue())
    }

    @Test
    fun `validation of formatted Value Nullable`() {
        // Arrange
        val id = "123456"
        val name = "Test Name"
        val prompt = "Test Prompt"
        val barCode = "1234567890"
        val paymentVoucher = "VOUCHER123"

        // Act
        val costsModel = CostsModel(
            id = id,
            name = name,
            prompt = prompt,
            barCode = barCode,
            paymentVoucher = paymentVoucher,
        )

        assertNotNull(costsModel.value)
        assertEquals("0,00", costsModel.formatValue())
    }
}
