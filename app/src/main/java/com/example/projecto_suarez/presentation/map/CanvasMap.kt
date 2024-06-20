package com.example.projecto_suarez.presentation.map

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val TAG = "MAP"

@Preview
@Composable
fun CanvasMap() {
    var point by remember { mutableStateOf(Pair(0f, 0f)) }
    var maxCanvasW = 0f
    var maxCanvasH = 0f

    Column {
        BoxWithConstraints {
            Canvas(
                modifier = Modifier
                    .size(maxWidth)
                    .padding(10.dp)
                    .pointerInput(Unit) {
                        detectTapGestures { offset ->
                            Log.d(TAG, "Se toca la pantalla en ${offset}")
                        }
                    }){

                maxCanvasW = size.width
                maxCanvasH = size.height

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
            picture(1, maxWidth*.1f, maxHeight*.1f, maxWidth, maxHeight)
            picture(2, maxWidth*.3f, maxHeight*.1f, maxWidth, maxHeight)
            picture(3, maxWidth*.5f, maxHeight*.1f, maxWidth, maxHeight)
            picture(4, maxWidth*.7f, maxHeight*.1f, maxWidth, maxHeight)
        }
        Button(onClick = { point = Pair((Math.random()*maxCanvasW).toFloat(), (Math.random()*maxCanvasH).toFloat()) }) {
            Text("Mi ubicación")
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

@Composable
fun picture(ID: Int, x: Dp, y: Dp, w: Dp, h: Dp) {
    Canvas(
        modifier = Modifier
            .size(w*.1f)
            .offset(x, y)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    Log.d(TAG, "Se toca la pintura ${ID} en ${offset}")
                }
            }
    ){
        drawRect(
            color = Color.Red
        )
    }
}
