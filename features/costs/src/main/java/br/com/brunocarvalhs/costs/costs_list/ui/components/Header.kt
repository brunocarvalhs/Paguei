package br.com.brunocarvalhs.costs.costs_list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R
import coil.compose.AsyncImage

@Composable
fun Header(
    name: String? = null,
    photoUrl: String? = null,
    onClickMenu: () -> Unit,
    content: @Composable () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.colorScheme.background
        ), startY = 0.0f, endY = 250.0f
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(10.dp)
        ) {
            Column {
                Text(text = stringResource(id = R.string.home_title_header, name.orEmpty()))
                Text(text = stringResource(R.string.costs_list_header_subtitle))
            }
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(54.dp)
                    .background(Color(0xFF585666), shape = CircleShape)
                    .clickable { onClickMenu.invoke() },
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(54.dp)
                        .background(Color(0xFF585666), shape = CircleShape)
                ) {
                    if (photoUrl.isNullOrEmpty()) {
                        Text(
                            text = name.orEmpty(), //.substring(0..1).uppercase(),
                            color = Color.White,
                        )
                    } else {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_drop_down_circle_24),
                    contentDescription = null,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradientBrush)
                .padding(vertical = 15.dp, horizontal = 10.dp)
        ) {
            content()
        }
    }
}

@Composable
@Preview
fun PreviewHeader() {
    PagueiTheme {
        Header(name = "bruno", photoUrl = null, onClickMenu = {}) {
            ReportCard(list = listOf(), onClick = {})
        }
    }
}