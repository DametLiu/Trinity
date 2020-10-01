package damet.android.trinity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import damet.android.mpp.MPPreference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MPPreference(this, "sp", "123").apply {
            setString("name", "lisa")
            setBoolean("girl", true)
            setInt("age", 15)
            setFloat("height", 185.7f)
            setObject("school", School("中学", "北京"))


            e(getString("name", ""))
            e(getBoolean("girl", false).toString())
            e(getInt("age", 0).toString())
            e(getFloat("height", 0.0f).toString())
            e(getObject("school", null).toString())
        }
    }

    private fun e(string: String) = Log.e(javaClass.simpleName, string)
}

data class School(val name:String, val loc:String)