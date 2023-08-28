package br.com.brunocarvalhs.groups.list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.domain.entities.UserEntities

@Composable
fun UserItem(user: UserEntities?, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color.White)
            .clickable { onClick.invoke() },
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
                    text = user?.initialsName().orEmpty(),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = user?.name.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Composable
@Preview
private fun PreviewUserItem() {
    PagueiTheme {
        UserItem(user = object : UserEntities {
            override val id: String
                get() = ""
            override val name: String?
                get() = "Jose Ramalho"
            override val photoUrl: String?
                get() = null
            override val email: String?
                get() = "email@email.com"
            override val salary: String?
                get() = null
            override val token: String?
                get() = ""

            override fun toMap(): Map<String?, Any?> = mapOf()
            override fun toJson(): String = ""

            override fun firstName(): String? = "Jose"

            override fun lastName(): String? = "Ramalho"

            override fun initialsName(): String? = "JR"

            override fun formatSalary(): String = ""

            override fun copyWith(
                name: String?, photoUrl: String?, email: String?, salary: String?
            ): UserEntities = this
        }, onClick = {})
    }
}