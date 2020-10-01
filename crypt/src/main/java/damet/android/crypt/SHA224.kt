package damet.android.crypt

import java.security.MessageDigest

object SHA224 {
    fun lower(string: String) : String = sha224(string, false)
    fun upper(string: String) : String = sha224(string, true)

    private fun sha224(string: String, upper: Boolean) : String =
        MessageDigest.getInstance(Algorithms.SHA224).apply { update(string.toByteArray()) }.digest() hex upper
}

fun String.sha224(upper: Boolean = false) : String = if (upper) SHA224.upper(this) else SHA224.lower(this)