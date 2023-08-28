package br.com.brunocarvalhs.costs.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.data.utils.sumOfFormatted
import br.com.brunocarvalhs.domain.entities.CostEntities
import coil.compose.AsyncImage

@Composable
fun TopHeader(
    name: String? = null,
    photoUrl: String? = null,
    isGroup: Boolean = false,
    list: List<CostEntities> = emptyList(),
    onClickMenu: () -> Unit,
    onReport: () -> Unit,
    onAdd: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(10.dp)
        ) {
            Column {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(id = R.string.home_title_header, name.orEmpty())
                )
                Text(
                    style = MaterialTheme.typography.titleSmall,
                    text = stringResource(R.string.costs_list_header_subtitle)
                )
            }
            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier
                    .size(54.dp)
                    .background(Color(0xFF585666), shape = RoundedCornerShape(10.dp))
                    .clickable { onClickMenu.invoke() },
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(54.dp)
                        .background(Color(0xFF585666), shape = RoundedCornerShape(10.dp))
                ) {
                    if (photoUrl.isNullOrEmpty() || isGroup) {
                        Text(
                            text = name.orEmpty().substring(0..1).uppercase(),
                            color = Color.White,
                        )
                    } else {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(10.dp))
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
                .background(Color.Transparent)
                .padding(vertical = 15.dp, horizontal = 10.dp)
        ) {
            Column {
                FloatingActionButton(onClick = onAdd) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(10.dp))
                FloatingActionButton(
                    onClick = onReport,
                ) {
                    Icon(imageVector = Icons.Default.ShowChart, contentDescription = null)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.labelSmall,
                            text = stringResource(id = R.string.home_top_total)
                        )
                        Text(
                            style = MaterialTheme.typography.headlineSmall, text = stringResource(
                                id = R.string.item_cost_value, list.sumOfFormatted()
                            )
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(vertical = 15.dp, horizontal = 10.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = stringResource(id = R.string.home_top_fix),
                    )
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(
                            id = R.string.item_cost_value,
                            list.sumOfFormatted { it.type == "FIX" })
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(120.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        style = MaterialTheme.typography.labelSmall,
                        text = stringResource(id = R.string.home_top_variable),
                    )
                    Text(
                        style = MaterialTheme.typography.headlineSmall,
                        text = stringResource(id = R.string.item_cost_value,
                            list.sumOfFormatted { it.type == "VARIABLE" }),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
fun PreviewTopHeader() {
    PagueiTheme {
        TopHeader(
            name = "bruno",
            photoUrl = null,
            list = emptyList(),
            onClickMenu = {},
            onAdd = {},
            onReport = {},
        )
    }
}