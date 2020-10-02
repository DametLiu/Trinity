package damet.android.trinity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import androidx.annotation.RequiresApi
import damet.android.crypt.*
import damet.android.mpp.MPPreference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        e("================= AES =================")
        val e = AES.encrptWithRandomIV("123", "123")
        e(e)
        val d = AES.decryptWithRandomIV(e, "123")
        e(d)

        e("================= MD5 =================")
        e(MD5.lower_16("123"))
        e(MD5.upper_16("123"))
        e(MD5.lower_32("123"))
        e(MD5.upper_32("123"))

        e("================= HEX =================")
        e("abc" hex true)
        e("abc" hex false)
        e("abc".toByteArray() hex true)
        e("abc".toByteArray() hex false)

        e("================= SHA =================")
        e(SHA1.lower("123"))
        e(SHA1.upper("123"))
        e(SHA224.lower("123"))
        e(SHA224.upper("123"))
        e(SHA256.lower("123"))
        e(SHA256.upper("123"))
        e(SHA384.lower("123"))
        e(SHA384.upper("123"))
        e(SHA512.lower("123"))
        e(SHA512.upper("123"))


        e("================= MPP =================")
        MPPreference(this, "sp").apply {
            setString("name", "lisa", "")
            setBoolean("girl", true, "123")
            setInt("age", 15, "123")
            setFloat("height", 185.7f, "123")
            setObject("school", School("中学", "北京"), "123")


            e(getString("name", "", ""))
            e(getBoolean("girl", false, "123").toString())
            e(getInt("age", 0, "123").toString())
            e(getFloat("height", 0.0f, "123").toString())
            e(getObject("school", School("高学", "上海"), "123").toString())
        }

        class MPPDelegate<T>(private val default: T, private val pwd:String = "") : ReadWriteProperty<Any?,T> {
            private val p = MPPreference(this@MainActivity, "bb")
            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
                when(value) {
                    is Boolean -> p.setBoolean(property.name, value, pwd)
                    is Float -> p.setFloat(property.name, value, pwd)
                    is Double -> p.setDouble(property.name, value, pwd)
                    is Int -> p.setInt(property.name, value, pwd)
                    is Long -> p.setLong(property.name, value, pwd)
                    is String -> p.setString(property.name, value, pwd)
                    else -> p.setObject(property.name, value, pwd)
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return when(default) {
                    is Boolean -> p.getBoolean(property.name, default, pwd) as T
                    is Float -> p.getFloat(property.name, default, pwd) as T
                    is Double -> p.getDouble(property.name, default, pwd) as T
                    is Int -> p.getInt(property.name, default, pwd) as T
                    is Long -> p.getLong(property.name, default, pwd) as T
                    is String -> p.getString(property.name, default, pwd) as T
                    else -> p.getObject(property.name, default, pwd)
                }
            }
        }

        var a : String by MPPDelegate("6")
        e(a)
        a = "1"
        e(a)

        var s by MPPDelegate(School("中学", "北京"),"123")
        e(s.toString())
        s = School("高学", "上海")
        e(s.toString())
    }

    private fun e(string: String) = Log.e(javaClass.simpleName, string)
}

data class School(val name:String, val loc:String)
