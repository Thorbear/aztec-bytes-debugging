package no.thorbear.aztec

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import no.thorbear.aztec.java.GraphicOverlay
import no.thorbear.aztec.java.GraphicOverlay.Graphic

/**
 * Graphic instance for rendering inference info (latency, FPS, resolution) in an overlay view.
 */
class InferenceInfoGraphic(
    private val overlay: GraphicOverlay,
    private val latency: Double,
    private val framesPerSecond: Int? // Only valid when a stream of input images is being processed. Null for single image mode.
) : Graphic(overlay) {

    private val textPaint: Paint = Paint()

    init {
        textPaint.color = TEXT_COLOR
        textPaint.textSize = TEXT_SIZE
        postInvalidate()
    }

    @Synchronized
    override fun draw(canvas: Canvas) {
        drawText(canvas, "InputImage size: ${overlay.imageWidth}x${overlay.imageHeight}", 0)
        // Draw FPS (if valid) and inference latency
        if (framesPerSecond != null) {
            drawText(canvas, "FPS: $framesPerSecond, latency: $latency ms", 1)
        } else {
            drawText(canvas, "Latency: $latency ms", 1)
        }
    }

    private fun drawText(canvas: Canvas, text: String, line: Int) {
        val x = TEXT_SIZE * 0.5f
        val y = TEXT_SIZE * 1.5f + TEXT_SIZE * line
        canvas.drawText(text, x, y, textPaint)
    }

    companion object {
        private const val TEXT_COLOR = Color.WHITE
        private const val TEXT_SIZE = 60.0f
    }
}
