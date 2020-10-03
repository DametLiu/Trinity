package damet.android.mpp

import android.content.Context
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference

class MPPreference(private val name: String) {
    companion object {
        private lateinit var context: Context
        private lateinit var content: String
        fun mppInit(ctx: Context, authority: String) {
            context = ctx
            content = "content://$authority/"
        }
    }

    private val accessor = PreferenceAccessor()
    fun setBoolean(key:String, value: Boolean, pwd:String = "") = accessor.setString(context, content, name, pwd, key, "$value")
    fun getBoolean(key: String, default: Boolean, pwd:String = "") : Boolean = accessor.getString(context, content, name, pwd, key, "$default").toBoolean()
    fun setInt(key: String, value: Int, pwd:String = "") = accessor.setString(context, content, name, pwd, key, "$value")
    fun getInt(key: String, default: Int, pwd:String = "") : Int = accessor.getString(context, content, name, pwd, key, "$default").toInt()
    fun setLong(key: String, value: Long, pwd:String = "") = accessor.setString(context, content, name, pwd, key, "$value")
    fun getLong(key: String, default: Long, pwd:String = "") : Long = accessor.getString(context, content, name, pwd, key, "$default").toLong()
    fun setFloat(key: String, value: Float, pwd:String = "") = accessor.setString(context, content, name, pwd, key, "$value")
    fun getFloat(key: String, default: Float, pwd:String = "") : Float = accessor.getString(context, content, name, pwd, key, "$default").toFloat()
    fun setDouble(key: String, value: Double, pwd:String = "") = accessor.setString(context, content, name, pwd, key, "$value")
    fun getDouble(key: String, default: Double, pwd:String = "") : Double = accessor.getString(context, content, name, pwd, key, "$default").toDouble()
    fun setString(key: String, value: String, pwd:String = "") = accessor.setString(context, content, name, pwd, key, value)
    fun getString(key: String, default: String, pwd:String = "") : String = accessor.getString(context, content, name, pwd, key, default)
    fun <T> setObject(key: String, value: T, pwd: String) = accessor.setString(context, content, name, pwd, key, JSON.toJSONString(value))
    fun <T> getObject(key: String, default: T, type: TypeReference<T>, pwd: String) : T {
        val str = accessor.getString(context, content, name, pwd, key, "")
        return if (str.isEmpty()) default
        else JSON.parseObject(str, type)
    }
    fun remove(key: String) = accessor.remove(context, content, name, key)
    fun clear() = accessor.clear(context, content, name)
}

