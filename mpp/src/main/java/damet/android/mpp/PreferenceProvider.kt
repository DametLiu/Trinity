package damet.android.mpp

import android.content.*
import android.content.pm.ProviderInfo
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.util.*

internal class PreferenceProvider : ContentProvider() {
    companion object {
        private val AUTHORITY : String
            get() = Properties().getProperty("AUTHORITY")
        private val CONTENT : String
            get() = Properties().getProperty("CONTENT")

        const val CONTENT_VALUES_KEY = "key"
        const val CONTENT_VALUES_VALUE = "value"

        fun buildUri(name: String, key: String): Uri =
            Uri.parse("$CONTENT$name/$key")
    }

    override fun attachInfo(context: Context?, info: ProviderInfo?) {
        super.attachInfo(context, info)
        Properties().setProperty("AUTHORITY", info!!.authority)
        Properties().setProperty("CONTENT", "content://${info!!.authority}/")
    }

    private fun sp(name: String) : SharedPreferences = context!!.getSharedPreferences(name, Context.MODE_PRIVATE)

    override fun onCreate(): Boolean = true

    private fun preferenceToCursor(value : String) : MatrixCursor = MatrixCursor(arrayOf(CONTENT_VALUES_VALUE), 1).apply {
        newRow().add(value)
    }

    @Synchronized override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String? ): Cursor? {
        var cursor : MatrixCursor? = null
        val sp = sp(uri.name)
        if (sp.contains(uri.key)) cursor = preferenceToCursor(sp.getString(uri.key, "")!!)
        return cursor
    }

    override fun getType(uri: Uri): String? = null

    @Synchronized override fun insert(uri: Uri, values: ContentValues?): Uri? = throw Exception("insert unsupport")

    @Synchronized override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0.apply { sp(uri.name).edit().remove(uri.key).apply() }
    }

    @Synchronized override fun update( uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>? ): Int {
        if (values == null) throw Exception("values cannot be null")
        return 0.apply { sp(uri.name).edit().putString(values.getAsString(CONTENT_VALUES_KEY), values.getAsString(CONTENT_VALUES_VALUE)).apply() }
    }
}

val Uri.name: String
    get() = pathSegments[0]

val Uri.key: String
    get() = pathSegments[1]