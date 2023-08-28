package br.com.brunocarvalhs.commons.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun SwipeItem(
    leftViewIcons: List<SwipeIcon> = emptyList(),
    rightViewIcons: List<SwipeIcon> = emptyList(),
    swipeDirection: SwipeDirection,
    leftViewWidth: Dp = 70.dp,
    rightViewWidth: Dp = 70.dp,
    height: Dp = 70.dp,
    leftViewBackgroundColor: Color = Color.Blue,
    rightViewBackgroundColor: Color = Color.Red,
    cornerRadius: Dp = 0.dp,
    leftSpace: Dp = 0.dp,
    rightSpace: Dp = 0.dp,
    fractionalThreshold: Float = 0.3f,
    content: @Composable () -> Unit
) {

    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val anchors = when (swipeDirection) {
        SwipeDirection.LEFT -> mapOf(
            0f to 0,
            -(with(LocalDensity.current) { rightViewWidth.toPx() }) to 1
        )

        SwipeDirection.RIGHT -> mapOf(
            0f to 0,
            (with(LocalDensity.current) { leftViewWidth.toPx() }) to 1
        )

        else -> mapOf(
            0f to 0,
            ((with(LocalDensity.current) { leftViewWidth.toPx() })) to 1,
            -(with(LocalDensity.current) { rightViewWidth.toPx() }) to 2
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .swipeable(
                        state = swipeAbleState,
                        anchors = anchors,
                        thresholds = { _, _ ->
                            FractionalThreshold(fractionalThreshold)
                        },
                        orientation = Orientation.Horizontal
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height),
                    horizontalArrangement = when (swipeDirection) {
                        SwipeDirection.BOTH -> Arrangement.SpaceBetween
                        SwipeDirection.LEFT -> Arrangement.End
                        else -> Arrangement.Start
                    },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (swipeDirection == SwipeDirection.RIGHT || swipeDirection == SwipeDirection.BOTH) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(leftViewWidth - leftSpace)
                                .background(
                                    leftViewBackgroundColor,
                                    shape = RoundedCornerShape(cornerRadius)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            leftViewIcons.forEachIndexed { _, triplet ->
                                IconButton(
                                    onClick = triplet.clickable,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        painter = triplet.icon,
                                        contentDescription = triplet.contentDescription,
                                        tint = triplet.tint
                                    )
                                }
                            }
                        }
                    }
                    if (swipeDirection == SwipeDirection.LEFT || swipeDirection == SwipeDirection.BOTH) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(rightViewWidth - rightSpace)
                                .background(
                                    rightViewBackgroundColor,
                                    shape = RoundedCornerShape(cornerRadius)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            rightViewIcons.forEachIndexed { _, triplet ->
                                IconButton(
                                    onClick = triplet.clickable,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        painter = triplet.icon,
                                        contentDescription = triplet.contentDescription,
                                        tint = triplet.tint
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                swipeAbleState.offset.value.roundToInt(), 0
                            )
                        }
                        .fillMaxWidth()
                ) {
                    content()
                }
            }
        }
    }
}

enum class SwipeDirection {
    LEFT, RIGHT, BOTH
}

data class SwipeIcon(
    val icon: Painter,
    val tint: Color = Color.Black,
    val contentDescription: String? = null,
    val clickable: () -> Unit = {},
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
private fun PreviewSwipeItem() {
    SwipeItem(swipeDirection = SwipeDirection.LEFT) { }
}