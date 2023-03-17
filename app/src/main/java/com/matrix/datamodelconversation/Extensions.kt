package com.matrix.datamodelconversation

import android.app.Activity
import java.io.IOException
import java.io.InputStream

fun Activity.loadJSONFromAsset(asset: String): String? {
    val json: String? = try {
        val `is`: InputStream = this.assets.open(asset)
        val size: Int = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer, charset("UTF-8"))
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}