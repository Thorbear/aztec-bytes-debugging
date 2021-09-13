package no.thorbear.aztec.preference

import android.hardware.Camera
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import no.thorbear.aztec.java.CameraSource
import no.thorbear.aztec.R
import no.thorbear.aztec.preference.PreferenceUtils.saveString
import java.util.HashMap

/**
 * Configures live preview demo settings.
 */
class LivePreviewPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference_live_preview_quickstart)
        setUpCameraPreferences()
    }

    private fun setUpCameraPreferences() {
        setUpCameraPreviewSizePreference(
            R.string.pref_key_rear_camera_preview_size,
            R.string.pref_key_rear_camera_picture_size,
            CameraSource.CAMERA_FACING_BACK
        )
        setUpCameraPreviewSizePreference(
            R.string.pref_key_front_camera_preview_size,
            R.string.pref_key_front_camera_picture_size,
            CameraSource.CAMERA_FACING_FRONT
        )
    }

    private fun setUpCameraPreviewSizePreference(
        @StringRes previewSizePrefKeyId: Int,
        @StringRes pictureSizePrefKeyId: Int,
        cameraId: Int
    ) {
        val previewSizePreference = findPreference(getString(previewSizePrefKeyId)) as ListPreference? ?: return
        var camera: Camera? = null
        try {
            camera = Camera.open(cameraId)
            val previewSizeList = CameraSource.generateValidPreviewSizeList(camera)
            val previewSizeStringValues = arrayOfNulls<String>(previewSizeList.size)
            val previewToPictureSizeStringMap: MutableMap<String, String> = HashMap()
            for (i in previewSizeList.indices) {
                val sizePair = previewSizeList[i]
                previewSizeStringValues[i] = sizePair.preview.toString()
                if (sizePair.picture != null) {
                    previewToPictureSizeStringMap[sizePair.preview.toString()] = sizePair.picture.toString()
                }
            }
            previewSizePreference.entries = previewSizeStringValues
            previewSizePreference.entryValues = previewSizeStringValues
            if (previewSizePreference.entry == null) {
                // First time of opening the Settings page.
                val sizePair = CameraSource.selectSizePair(
                    camera,
                    CameraSource.DEFAULT_REQUESTED_CAMERA_PREVIEW_WIDTH,
                    CameraSource.DEFAULT_REQUESTED_CAMERA_PREVIEW_HEIGHT
                )
                val previewSizeString = sizePair.preview.toString()
                previewSizePreference.value = previewSizeString
                previewSizePreference.summary = previewSizeString
                saveString(
                    requireContext(),
                    pictureSizePrefKeyId,
                    if (sizePair.picture != null) sizePair.picture.toString() else null
                )
            } else {
                previewSizePreference.summary = previewSizePreference.entry
            }
            previewSizePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _: Preference?, newValue: Any ->
                    val newPreviewSizeStringValue = newValue as String
                    previewSizePreference.summary = newPreviewSizeStringValue
                    saveString(
                        requireContext(),
                        pictureSizePrefKeyId,
                        previewToPictureSizeStringMap[newPreviewSizeStringValue]
                    )
                    true
                }
        } catch (e: Exception) {
            // If there's no camera for the given camera id, hide the corresponding preference.
            val category = findPreference(getString(R.string.pref_category_key_camera)) as PreferenceCategory?
            category?.removePreference(previewSizePreference)
        } finally {
            camera?.release()
        }
    }
}
