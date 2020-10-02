package damet.android.mpp

import android.content.Context

class MPPreference(private val context: Context, private val name: String) {
    private val accessor = PreferenceAccessor()
    fun setBoolean(pwd: String, key:String, value: Boolean) = accessor.setBoolean(context, name, pwd, key, value)
    fun getBoolean(pwd: String, key: String, default: Boolean) : Boolean = accessor.getBoolean(context, name, pwd, key, default)
    fun setInt(pwd: String, key: String, value: Int) = accessor.setInt(context, name, pwd, key, value)
    fun getInt(pwd: String, key: String, default: Int) : Int = accessor.getInt(context, name, pwd, key, default)
    fun setLong(pwd: String, key: String, value: Long) = accessor.setLong(context, name, pwd, key, value)
    fun getLong(pwd: String, key: String, default: Long) : Long = accessor.getLong(context, name, pwd, key, default)
    fun setFloat(pwd: String, key: String, value: Float) = accessor.setFloat(context, name, pwd, key, value)
    fun getFloat(pwd: String, key: String, default: Float) : Float = accessor.getFloat(context, name, pwd, key, default)
    fun setDouble(pwd: String, key: String, value: Double) = accessor.setDouble(context, name, pwd, key, value)
    fun getDouble(pwd: String, key: String, default: Double) = accessor.getDouble(context, name, pwd, key, default)
    fun setString(pwd: String, key: String, value: String) = accessor.setString(context, name, pwd, key, value)
    fun getString(pwd: String, key: String, default: String) : String = accessor.getString(context, name, pwd, key, default)
    fun <T> setObject(pwd: String, key: String, value: T) = accessor.setObject(context, name, pwd, key, value)
    fun <T> getObject(pwd: String, key: String, default: T) : T = accessor.getObject(context, name, pwd, key, default)
    fun remove(key: String) = accessor.remove(context, name, key)
    fun clear() = accessor.clear(context, name)
}