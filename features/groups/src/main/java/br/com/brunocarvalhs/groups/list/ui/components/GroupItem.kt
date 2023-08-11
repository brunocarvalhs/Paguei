package br.com.brunocarvalhs.groups.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.domain.entities.GroupEntities

@Composable
fun GroupItem(
    group: GroupEntities,
    onClick: (group: GroupEntities) -> Unit,
    onLeft: (group: GroupEntities) -> Unit,
    onRight: (group: GroupEntities) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color.White)
            .clickable { onClick.invoke(group) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()

                    val (x) = dragAmount
                    when {
                        x > 0 -> onRight.invoke(group)
                        x < 0 -> onLeft.invoke(group)
                    }

                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(Color(0xFF585666), shape = CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "BR",
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = group.name.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Composable
@Preview
private fun PreviewGroupItem() {
    PagueiTheme {
        GroupItem(group = object : GroupEntities {
            override val id: String
                get() = ""
            override val name: String
                get() = "TESTE"
            override val members: List<String>
                get() = emptyList()

            override fun toMap(): Map<String?, Any?> = mapOf()
            override fun toJson(): String = ""
            override fun copyWith(name: String?, members: List<String>): GroupEntities = this
        }, onClick = {}, onLeft = {}, onRight = {})
    }
}