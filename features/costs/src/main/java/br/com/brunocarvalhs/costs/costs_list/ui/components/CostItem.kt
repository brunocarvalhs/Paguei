package br.com.brunocarvalhs.costs.costs_list.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.components.SwipeDirection
import br.com.brunocarvalhs.commons.components.SwipeIcon
import br.com.brunocarvalhs.commons.components.SwipeItem
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.domain.entities.CostEntities

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CostItem(
    cost: CostEntities,
    onClick: (cost: CostEntities) -> Unit,
    onLongClick: (cost: CostEntities) -> Unit,
    onLeft: (cost: CostEntities) -> Unit,
    onRight: (cost: CostEntities) -> Unit
) {
    SwipeItem(
        swipeDirection = SwipeDirection.BOTH,
        leftViewBackgroundColor = Color.DarkGray,
        rightViewBackgroundColor = Color.Red,
        rightViewIcons = arrayListOf(
            SwipeIcon(
                icon = painterResource(id = R.drawable.ic_baseline_delete_24),
                tint = Color.White,
                contentDescription = null,
                clickable = { onRight.invoke(cost) }
            )
        ),
        leftViewIcons = arrayListOf(
            SwipeIcon(
                icon = painterResource(id = R.drawable.ic_baseline_payment_24),
                tint = Color.White,
                contentDescription = null,
                clickable = { onLeft.invoke(cost) }
            )
        ),
        height = 90.dp,
        leftViewWidth = 90.dp,
        rightViewWidth = 90.dp,
        cornerRadius = 8.0.dp,
    ) {
        Card(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .combinedClickable(
                    onClick = { onClick.invoke(cost) },
                    onLongClick = { onLongClick.invoke(cost) },
                )
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = cost.name.orEmpty())
                    Text(text = stringResource(id = R.string.item_cost_value, cost.formatValue()))
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.item_cost_date, cost.prompt.orEmpty()))
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCostItem() {
    PagueiTheme {
        CostItem(cost = object : CostEntities {
            override val id: String
                get() = "1228215e-32f3-4135-a584-242defdb9431"
            override val name: String
                get() = "TESTE"
            override val type: String
                get() = "FIX"
            override val prompt: String
                get() = "01/02/2023"
            override val value: String
                get() = "200.00"
            override val barCode: String
                get() = "2387138972839712897328197"
            override val paymentVoucher: String
                get() = "teste"
            override val datePayment: String
                get() = "25/02/2023"
            override val dateReferenceMonth: String
                get() = "02/2023"

            override fun toMap(): Map<String?, Any?> = mapOf()
            override fun toJson(): String = ""
            override fun formatValue(): String = "200.00"

            override fun copyWith(
                name: String?,
                type: String?,
                prompt: String?,
                value: String?,
                barCode: String?,
                paymentVoucher: String?,
                datePayment: String?,
                dateReferenceMonth: String?
            ): CostEntities = this

        }, onClick = {}, onLeft = {}, onRight = {}, onLongClick = {})
    }
}