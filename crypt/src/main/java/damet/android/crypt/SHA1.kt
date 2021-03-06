package damet.android.crypt

import java.security.MessageDigest

object SHA1 {
    private val sha1 = MessageDigest.getInstance(Algorithms.SHA1)

    fun lower(string: String) : String = sha1(string, false)
    fun upper(string: String) : String = sha1(string, true)

    private fun sha1(string: String, upper: Boolean) : String =
        sha1.apply { update(string.toByteArray()) }.digest() hex upper
}

fun String.sha1(upper: Boolean = false) : String = if (upper) SHA1.upper(this) else SHA1.lower(this)