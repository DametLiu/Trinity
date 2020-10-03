package damet.android.mpp

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import damet.android.mpp.CryptConverter.decode
import damet.android.mpp.CryptConverter.encode
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_KEY
import damet.android.mpp.PreferenceProvider.Companion.CONTENT_VALUES_VALUE
import damet.android.mpp.PreferenceProvider.Companion.buildUri

internal class PreferenceAccessor() {

    private fun obtain(key: String, value: String, pwd: String) : ContentValues {
        return ContentValues().apply {
            put(CONTENT_VALUES_KEY, key)
            put(CONTENT_VALUES_VALUE, encode(value, pwd))
        }
    }

    private fun get(context: Context, content: String, name: String, key: String, default: String, pwd: String) : String {
        var result = default
        val cursor = context.contentResolver.query(buildUri(content, name, key))
        if (cursor != null && cursor.moveToFirst())
            result = decode(cursor.getString(cursor.getColumnIndex(CONTENT_VALUES_VALUE)), default, pwd)
        if (cursor != null && !cursor.isClosed) cursor.close()
        return result
    }

    fun setString(context: Context, content: String, name: String, pwd: String, key: String, value: String) =
        context.contentResolver.update(buildUri(content, name, key), obtain(key, value, pwd))

    fun getString(context: Context, content: String, name: String, pwd: String, key: String, default: String) : String =
        get(context, content, name, key, default, pwd)

    fun remove(context: Context, content: String, name: String, key: String) =
        context.contentResolver.delete(buildUri(content, name, key), null, null)

    fun clear(context: Context, content: String, name: String) =
        context.contentResolver.delete(buildUri(content, name, "*"), null, null)

}

fun ContentResolver.update(uri: Uri, contentValues: ContentValues) = update(uri, contentValues, null, null)
fun ContentResolver.query(uri: Uri) : Cursor? = query(uri, null, null, null, null)