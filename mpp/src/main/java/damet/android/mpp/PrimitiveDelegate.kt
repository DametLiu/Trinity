package damet.android.mpp

import android.content.Context
import damet.android.crypt.md5
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class PrimitiveDelegate<T>(private val default: T) : ReadWriteProperty<Any?, T> {
    protected abstract val context : Context
    protected abstract val authority : String
    protected abstract val name : String
    protected abstract val cryptKey: String
    private val p by lazy { MPPreference(name).apply { MPPreference.mppInit(context, authority) } }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        when(value) {
            is Boolean -> p.setBoolean(property.name.md5(), value, cryptKey)
            is Float -> p.setFloat(property.name.md5(), value, cryptKey)
            is Double -> p.setDouble(property.name.md5(), value, cryptKey)
            is Int -> p.setInt(property.name.md5(), value, cryptKey)
            is Long -> p.setLong(property.name.md5(), value, cryptKey)
            is String -> p.setString(property.name.md5(), value, cryptKey)
            else -> throw Exception("unsupport type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return when(default) {
            is Boolean -> p.getBoolean(property.name.md5(), default, cryptKey) as T
            is Float -> p.getFloat(property.name.md5(), default, cryptKey) as T
            is Double -> p.getDouble(property.name.md5(), default, cryptKey) as T
            is Int -> p.getInt(property.name.md5(), default, cryptKey) as T
            is Long -> p.getLong(property.name.md5(), default, cryptKey) as T
            is String -> p.getString(property.name.md5(), default, cryptKey) as T
            else -> throw Exception("unsupport type")
        }
    }
}