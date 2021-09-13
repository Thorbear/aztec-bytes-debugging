package no.thorbear.aztec

import android.graphics.Bitmap
import android.graphics.Canvas
import no.thorbear.aztec.java.GraphicOverlay
import no.thorbear.aztec.java.GraphicOverlay.Graphic

/**
 * Draw camera image to background.
 */
class CameraImageGraphic(overlay: GraphicOverlay, private val bitmap: Bitmap) : Graphic(overlay) {
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, transformationMatrix, null)
    }
}
