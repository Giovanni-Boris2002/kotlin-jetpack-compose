package com.example.projecto_suarez.presentation.map

import android.graphics.Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Map() {
    var point by remember { mutableStateOf(Pair(0f, 0f)) }

    BoxWithConstraints {
        Canvas(
            modifier = Modifier
                .size(maxWidth)
                .padding(10.dp)) {

            var path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            drawPath(
                path = path,
                color = Color.LightGray,
                style = Stroke(width = 5.dp.toPx())
            )

            path = Path().apply {
                moveTo(0f, size.height*.5f)
                lineTo(0f, size.height*(.5f-.15f))

                close()
            }

            drawPath(
                path = path,
                color = Color.Black,
                style = Stroke(width = 5.dp.toPx())
            )

            point?.let { (x, y) ->
                drawPoint(x, y, Color.Red)
            }
        }
    }
}

fun DrawScope.drawPoint(x: Float, y: Float, color: Color) {
    drawCircle(
        color = color,
        center = Offset(x, y),
        radius = 5.dp.toPx()
    )
}
