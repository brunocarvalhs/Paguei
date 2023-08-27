package br.com.brunocarvalhs.costs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.commons.theme.PagueiTheme
import br.com.brunocarvalhs.costs.R
import br.com.brunocarvalhs.domain.entities.CostEntities

@Composable
fun ReportCard(
    list: List<CostEntities>,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 90.dp, max = 100.dp)
            .clickable { onClick.invoke() },
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Divider(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .width(1.dp)
                        .height(32.dp)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text = stringResource(
                        id = R.string.costs_total_text,
                        list.size.toString()
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewReportCard() {
    PagueiTheme {
        ReportCard(list = listOf(), onClick = {})
    }
}