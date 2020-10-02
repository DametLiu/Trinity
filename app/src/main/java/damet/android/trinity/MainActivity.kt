package damet.android.trinity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import damet.android.crypt.*
import damet.android.mpp.MPPreference

class MainActivity : AppCompatActivity() {
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
            setString("","name", "lisa")
            setBoolean("123","girl", true)
            setInt("123","age", 15)
            setFloat("123","height", 185.7f)
            setObject("123","school", School("中学", "北京"))


            e(getString("","name", ""))
            e(getBoolean("123","girl", false).toString())
            e(getInt("123","age", 0).toString())
            e(getFloat("123","height", 0.0f).toString())
            e(getObject("123","school", null).toString())
        }
    }

    private fun e(string: String) = Log.e(javaClass.simpleName, string)
}

data class School(val name:String, val loc:String)