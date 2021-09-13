package no.thorbear.aztec.preference

import android.content.Context
import androidx.annotation.StringRes
import androidx.preference.PreferenceManager
import com.google.android.gms.common.images.Size
import no.thorbear.aztec.java.CameraSource
import no.thorbear.aztec.java.CameraSource.SizePair
import no.thorbear.aztec.Preconditions.checkArgument
import no.thorbear.aztec.R
import java.lang.NumberFormatException

/**
 * Utility class to retrieve shared preferences.
 */
object PreferenceUtils {

    fun saveString(context: Context, @StringRes prefKeyId: Int, value: String?) {
        PreferenceManager.getDefaultSharedPreferences(context)
            .edit()
            .putString(context.getString(prefKeyId), value)
            .apply()
    }

    @JvmStatic
    fun getCameraPreviewSizePair(context: Context, cameraId: Int): SizePair? {
        checkArgument(cameraId == CameraSource.CAMERA_FACING_BACK || cameraId == CameraSource.CAMERA_FACING_FRONT)
        val previewSizePrefKey: String
        val pictureSizePrefKey: String
        if (cameraId == CameraSource.CAMERA_FACING_BACK) {
            previewSizePrefKey = context.getString(R.string.pref_key_rear_camera_preview_size)
            pictureSizePrefKey = context.getString(R.string.pref_key_rear_camera_picture_size)
        } else {
            previewSizePrefKey = context.getString(R.string.pref_key_front_camera_preview_size)
            pictureSizePrefKey = context.getString(R.string.pref_key_front_camera_picture_size)
        }
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val previewSize = sharedPreferences.getString(previewSizePrefKey, null) ?: return null
        val pictureSize = sharedPreferences.getString(pictureSizePrefKey, null) ?: return null
        return try {
            SizePair(Size.parseSize(previewSize), Size.parseSize(pictureSize))
        } catch (e: NumberFormatException) {
            null
        }
    }

    @JvmStatic
    fun isCameraLiveViewportEnabled(context: Context): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefKey = context.getString(R.string.pref_key_camera_live_viewport)
        return sharedPreferences.getBoolean(prefKey, false)
    }
}
