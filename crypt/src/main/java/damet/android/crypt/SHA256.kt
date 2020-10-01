package damet.android.crypt

import java.security.MessageDigest

object SHA256 {
    fun lower(string: String) : String = sha256(string, false)
    fun upper(string: String) : String = sha256(string, true)

    private fun sha256(string: String, upper: Boolean) : String =
        MessageDigest.getInstance(Algorithms.SHA256).apply { update(string.toByteArray()) }.digest() hex upper
}

fun String.sha256(upper: Boolean = false) : String = if (upper) SHA256.upper(this) else SHA256.lower(this)