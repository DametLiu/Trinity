package damet.android.crypt

import java.security.MessageDigest

object SHA512 {
    fun lower(string: String) : String = sha512(string, false)
    fun upper(string: String) : String = sha512(string, true)

    private fun sha512(string: String, upper: Boolean) : String =
        MessageDigest.getInstance(Algorithms.SHA512).apply { update(string.toByteArray()) }.digest() hex upper
}

fun String.sha512(upper: Boolean = false) : String = if (upper) SHA512.upper(this) else SHA512.lower(this)