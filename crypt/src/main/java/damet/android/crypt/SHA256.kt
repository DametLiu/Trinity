package damet.android.crypt

import java.security.MessageDigest

object SHA256 {
    fun lower(string: String) : String = sha256(string, false)
    fun upper(string: String) : String = sha256(string, true)

    private fun sha256(string: String, upper: Boolean) : String {
        val result = MessageDigest.getInstance("SHA-256").apply { update(string.toByteArray()) }.digest() hex upper
        return if (32 > result.length) result else result.substring(0, 32)
    }
}