package no.thorbear.aztec

import com.google.mlkit.common.MlKitException
import no.thorbear.aztec.java.GraphicOverlay
import java.nio.ByteBuffer

/**
 * An interface to process the images with different vision detectors and custom image models.
 */
interface VisionImageProcessor {

    /**
     * Processes ByteBuffer image data, e.g. used for Camera1 live preview case.
     */
    @Throws(MlKitException::class)
    fun processByteBuffer(data: ByteBuffer?, frameMetadata: FrameMetadata?, graphicOverlay: GraphicOverlay)

    /**
     * Stops the underlying machine learning model and release resources.
     */
    fun stop()
}
