package damet.android.crypt

import java.security.MessageDigest

object SHA384 {
    private val sha384 = MessageDigest.getInstance(Algorithms.SHA384)

    fun lower(string: String) : String = sha384(string, false)
    fun upper(string: String) : String = sha384(string, true)

    private fun sha384(string: String, upper: Boolean) : String =
        sha384.apply { update(string.toByteArray()) }.digest() hex upper
}

fun String.sha384(upper: Boolean = false) : String = if (upper) SHA384.upper(this) else SHA384.lower(this)