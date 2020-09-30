package damet.android.mpp

import android.content.Context

class MPPreference(private val context: Context, private val name: String, key: String) {
    private val accessor = PreferenceAccessor(key)
    fun setBoolean(key:String, value: Boolean) = accessor.setBoolean(context, name, key, value)
    fun getBoolean(key: String, default: Boolean) : Boolean = accessor.getBoolean(context, name, key, default)
    fun setInt(key: String, value: Int) = accessor.setInt(context, name, key, value)
    fun getInt(key: String, default: Int) : Int = accessor.getInt(context, name, key, default)
    fun setLong(key: String, value: Long) = accessor.setLong(context, name, key, value)
    fun getLong(key: String, default: Long) : Long = accessor.getLong(context, name, key, default)
    fun setFloat(key: String, value: Float) = accessor.setFloat(context, name, key, value)
    fun getFloat(key: String, default: Float) : Float = accessor.getFloat(context, name, key, default)
    fun setDouble(key: String, value: Double) = accessor.setDouble(context, name, key, value)
    fun getDouble(key: String, default: Double) = accessor.getDouble(context, name, key, default)
    fun setString(key: String, value: String) = accessor.setString(context, name, key, value)
    fun getString(key: String, default: String) : String = accessor.getString(context, name, key, default)
    fun <T> setObject(key: String, value: T) = accessor.setObject(context, name, key, value)
    fun <T> getObject(key: String, default: T) : T = accessor.getObject(context, name, key, default)
}