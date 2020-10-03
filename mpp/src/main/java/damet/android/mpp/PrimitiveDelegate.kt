package damet.android.mpp

import android.content.Context
import damet.android.crypt.md5
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class PrimitiveDelegate<T>(private val default: T, private val pwd:String = "") : ReadWriteProperty<Any?, T> {
    protected abstract var context : Context
    protected abstract var authority : String
    protected abstract var name : String
    private val p by lazy { MPPreference(name).apply { MPPreference.mppInit(context, authority) } }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when(value) {
            is Boolean -> p.setBoolean(property.name.md5(true), value, pwd)
            is Float -> p.setFloat(property.name.md5(true), value, pwd)
            is Double -> p.setDouble(property.name.md5(true), value, pwd)
            is Int -> p.setInt(property.name.md5(true), value, pwd)
            is Long -> p.setLong(property.name.md5(true), value, pwd)
            is String -> p.setString(property.name.md5(true), value, pwd)
            else -> throw Exception("unsupport type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when(default) {
            is Boolean -> p.getBoolean(property.name.md5(true), default, pwd) as T
            is Float -> p.getFloat(property.name.md5(true), default, pwd) as T
            is Double -> p.getDouble(property.name.md5(true), default, pwd) as T
            is Int -> p.getInt(property.name.md5(true), default, pwd) as T
            is Long -> p.getLong(property.name.md5(true), default, pwd) as T
            is String -> p.getString(property.name.md5(true), default, pwd) as T
            else -> throw Exception("unsupport type")
        }
    }
}