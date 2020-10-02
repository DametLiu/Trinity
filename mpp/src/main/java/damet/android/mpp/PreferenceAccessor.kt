package damet.android.mpp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.alibaba.fastjson.TypeReference
import damet.android.mpp.ObjectConverter.decode
import damet.android.mpp.ObjectConverter.encode
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_KEY
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_VALUE
import damet.android.mpp.PreferenceProvider.Companion.buildUri

internal class PreferenceAccessor() {

    private fun <T> obtain(key: String, value: T, pwd: String) : ContentValues {
        return ContentValues().apply {
            put(CONTENT_VALUES_KEY, key)
            put(CONTENT_VALUES_VALUE, encode(value, pwd))
        }
    }

    private fun <T> get(context: Context, name: String, key: String, default: T, pwd: String) : T {
        var result = default
        val cursor = context.contentResolver.query(buildUri(name, key))
        if (cursor != null && cursor.moveToFirst())
            result = decode(cursor.getString(cursor.getColumnIndex(CONTENT_VALUES_VALUE)), default, pwd)
        if (cursor != null && !cursor.isClosed) cursor.close()
        return result
    }

    fun setBoolean(context: Context, name: String, pwd: String, key:String, value: Boolean) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getBoolean(context: Context, name: String, pwd: String, key: String, default: Boolean) : Boolean =
        get(context, name, key, default, pwd)

    fun setInt(context: Context, name: String, pwd: String, key: String, value: Int) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getInt(context: Context, name: String, pwd: String, key: String, default: Int) : Int =
        get(context, name, key, default, pwd)

    fun setLong(context: Context, name: String, pwd: String, key: String, value: Long) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getLong(context: Context, name: String, pwd: String, key: String, default: Long) : Long =
        get(context, name, key, default, pwd)

    fun setFloat(context: Context, name: String, pwd: String, key: String, value: Float) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getFloat(context: Context, name: String, pwd: String, key: String, default: Float) : Float =
        get(context, name, key, default, pwd)

    fun setDouble(context: Context, name: String, pwd: String, key: String, value: Double) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getDouble(context: Context, name: String, pwd: String, key: String, default: Double) =
        get(context, name, key, default, pwd)

    fun setString(context: Context, name: String, pwd: String, key: String, value: String) =
        context.contentResolver.update(buildUri(name, key), obtain(key, value, pwd))

    fun getString(context: Context, name: String, pwd: String, key: String, default: String) : String =
        get(context, name, key, default, pwd)

    fun remove(context: Context, name: String, key: String) =
        context.contentResolver.delete(buildUri(name, key), null, null)

    fun clear(context: Context, name: String) =
        context.contentResolver.delete(buildUri(name, "*"), null, null)

}

fun ContentResolver.update(uri: Uri, contentValues: ContentValues) = update(uri, contentValues, null, null)
fun ContentResolver.query(uri: Uri) : Cursor? = query(uri, null, null, null, null)