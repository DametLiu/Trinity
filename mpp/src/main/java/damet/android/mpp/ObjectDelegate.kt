package damet.android.mpp

import android.content.Context
import com.alibaba.fastjson.TypeReference
import damet.android.crypt.md5
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class ObjectDelegate<T>(private val default: T, private val type: TypeReference<T>, private val pwd:String = "") : ReadWriteProperty<Any?, T> {
    protected abstract var context : Context
    protected abstract var authority : String
    protected abstract var name : String
    private val p by lazy { MPPreference(name).apply { MPPreference.mppInit(context, authority) } }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        p.setObject(property.name.md5(true), value, pwd)

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        p.getObject(property.name.md5(true), default, type, pwd)
}