package damet.android.mpp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import damet.android.mpp.ObjectConverter.decode
import damet.android.mpp.ObjectConverter.encode
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_KEY
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_VALUE
import damet.android.mpp.PreferenceProvider.Companion.buildUri

internal class PreferenceAccessor(private val pwd:String) {

    private fun <T> obtain(key: String, value: T) : ContentValues {
        return ContentValues().apply {
            put(CONTENT_VALUES_KEY, key)
            put(CONTENT_VALUES_VALUE, encode(value, pwd))
        }
    }

    private fun <T> get(context: Context, name: String, key: String, default: T) : T {
        var result = default
        val cursor = context.contentResolver.query(buildUri(name, key))
        if (cursor != null && cursor.moveToFirst())
            result = decode(cursor.getString(cursor.getColumnIndex(CONTENT_VALUES_VALUE)), default, pwd)
        if (!cursor!!.isClosed) cursor.close()
        return result
    }

    fun setBoolean(context: Context, name: String, key:String, value: Boolean) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getBoolean(context: Context, name: String, key: String, default: Boolean) : Boolean =
        get(context, name, key, default)

    fun setInt(context: Context, name: String, key: String, value: Int) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getInt(context: Context, name: String, key: String, default: Int) : Int =
        get(context, name, key, default)

    fun setLong(context: Context, name: String, key: String, value: Long) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getLong(context: Context, name: String, key: String, default: Long) : Long =
        get(context, name, key, default)

    fun setFloat(context: Context, name: String, key: String, value: Float) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getFloat(context: Context, name: String, key: String, default: Float) : Float =
        get(context, name, key, default)

    fun setDouble(context: Context, name: String, key: String, value: Double) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getDouble(context: Context, name: String, key: String, default: Double) =
        get(context, name, key, default)

    fun setString(context: Context, name: String, key: String, value: String) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun getString(context: Context, name: String, key: String, default: String) : String =
        get(context, name, key, default)

    fun <T> setObject(context: Context, name: String, key: String, value: T) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value))

    fun <T> getObject(context: Context, name: String, key: String, default: T) : T =
        get(context, name, key, default)
}

fun ContentResolver.update(uri: Uri, contentValues: ContentValues) = update(uri, contentValues, null, null)
fun ContentResolver.query(uri: Uri) : Cursor? = query(uri, null, null, null, null)