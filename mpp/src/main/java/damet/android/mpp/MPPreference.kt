package damet.android.mpp

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

class MPPreference(private val name: String) {
    companion object {
        private lateinit var context: Context
        fun mppInit(ctx: Context) {
            context = ctx
        }
    }

    private val accessor = PreferenceAccessor()
    fun setBoolean(key:String, value: Boolean, pwd:String = "") = accessor.setBoolean(context, name, pwd, key, value)
    fun getBoolean(key: String, default: Boolean, pwd:String = "") : Boolean = accessor.getBoolean(context, name, pwd, key, default)
    fun setInt(key: String, value: Int, pwd:String = "") = accessor.setInt(context, name, pwd, key, value)
    fun getInt(key: String, default: Int, pwd:String = "") : Int = accessor.getInt(context, name, pwd, key, default)
    fun setLong(key: String, value: Long, pwd:String = "") = accessor.setLong(context, name, pwd, key, value)
    fun getLong(key: String, default: Long, pwd:String = "") : Long = accessor.getLong(context, name, pwd, key, default)
    fun setFloat(key: String, value: Float, pwd:String = "") = accessor.setFloat(context, name, pwd, key, value)
    fun getFloat(key: String, default: Float, pwd:String = "") : Float = accessor.getFloat(context, name, pwd, key, default)
    fun setDouble(key: String, value: Double, pwd:String = "") = accessor.setDouble(context, name, pwd, key, value)
    fun getDouble(key: String, default: Double, pwd:String = "") = accessor.getDouble(context, name, pwd, key, default)
    fun setString(key: String, value: String, pwd:String = "") = accessor.setString(context, name, pwd, key, value)
    fun getString(key: String, default: String, pwd:String = "") : String = accessor.getString(context, name, pwd, key, default)
    fun <T> setObject(key: String, value: T, pwd: String) = accessor.setString(context, name, pwd, key, JSON.toJSONString(value))
    fun <T> getObject(key: String, default: T, type: TypeReference<T>, pwd: String) : T {
        val str = accessor.getString(context, name, pwd, key, "")
        return if (str.isEmpty()) default
        else JSON.parseObject(str, type)
    }
    fun remove(key: String) = accessor.remove(context, name, key)
    fun clear() = accessor.clear(context, name)
}

