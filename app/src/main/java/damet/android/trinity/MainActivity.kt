package damet.android.trinity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
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


    }

    private fun e(string: String) = Log.e(javaClass.simpleName, string)
}

data class School(val name:String, val loc:String)
