package damet.android.mpp

import android.content.Context
import com.alibaba.fastjson.TypeReference
import damet.android.crypt.md5
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class ObjectDelegate<T>(private val default: T, private val type: TypeReference<T>) : ReadWriteProperty<Any?, T> {
    protected abstract var context : Context
    protected abstract var authority : String
    protected abstract var name : String
    protected abstract var cryptKey: String
    private val p by lazy { MPPreference(name).apply { MPPreference.mppInit(context, authority) } }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        p.setObject(property.name.md5(), value, cryptKey)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        p.getObject(property.name.md5(), default, type, cryptKey)
}