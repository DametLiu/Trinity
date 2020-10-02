@file:Suppress("UNCHECKED_CAST", "NAME_SHADOWING")

package damet.android.mpp

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import damet.android.crypt.AES

internal object ObjectConverter {
    fun <T> encode(obj : T, pwd: String) : String {
        val str = when(obj) {
            is Boolean, Float, Double, Int, Long -> "$obj"
            is String -> obj
            else -> JSON.toJSONString(obj)
        }
        return if (pwd.isEmpty()) AES.encrptWithRandomIV(str, pwd) else str
    }

    fun <T> decode(str : String, default: T, pwd: String) : T {
        val str = if (pwd.isEmpty()) AES.decryptWithRandomIV(str, pwd) else str
        return when(default) {
            is Boolean -> str.toBoolean() as T
            is Float -> str.toFloat() as T
            is Double -> str.toDouble() as T
            is Int -> str.toInt() as T
            is Long -> str.toLong() as T
            is String -> str as T
            else -> JSON.parseObject(str, object : TypeReference<T>() {})
        }
    }
}