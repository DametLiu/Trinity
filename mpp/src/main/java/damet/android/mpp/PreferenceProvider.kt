package damet.android.mpp

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri

class PreferenceProvider : ContentProvider() {
    companion object {
        private const val AUTHORITY = "damet.android.mmp.provider"
        private const val CONTENT = "content://$AUTHORITY/"

        const val CONTENT_VALUES_KEY = "key"
        const val CONTENT_VALUES_VALUE = "value"

        fun buildUri(name: String, key: String): Uri =
            Uri.parse("$CONTENT$name/$key")
    }

    private fun sp(name: String) : SharedPreferences = context!!.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun onCreate(): Boolean = true

    private fun preferenceToCursor(value : String) : MatrixCursor = MatrixCursor(arrayOf(CONTENT_VALUES_VALUE), 1).apply {
        newRow().add(value)
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String? ): Cursor? {
        println(uri.toString())
        var cursor : MatrixCursor? = null
        val sp = sp(uri.name)
        if (sp.contains(uri.key)) cursor = preferenceToCursor(sp.getString(uri.key, "")!!)
        return cursor
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = throw Exception("insert unsupport")

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0.apply { sp(uri.name).edit().remove(uri.key).apply() }
    }

    override fun update( uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>? ): Int {
        if (values == null) throw Exception("values cannot be null")
        return 0.apply { sp(uri.name).edit().putString(values.getAsString(CONTENT_VALUES_KEY), values.getAsString(CONTENT_VALUES_VALUE)).apply() }
    }
}

val Uri.name: String
    get() = pathSegments[0]

val Uri.key: String
    get() = pathSegments[1]