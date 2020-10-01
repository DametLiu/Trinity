package damet.android.crypt

import java.security.MessageDigest

object MD5 {
    fun lower_16(string: String) : String = md5(string, 8, 24)
    fun upper_16(string: String) : String = md5(string, 8, 24, true)

    fun lower_32(string: String) : String = md5(string, 0, 32)
    fun upper_32(string: String) : String = md5(string, 0, 32, true)

    private fun md5(string: String, start: Int, end: Int, upper: Boolean = false) : String {
        if (start in 0..32 && end in 0..32)
            return MessageDigest.getInstance("MD5").apply { update(string.toByteArray()) }.digest().hex(upper).substring(start, end)
        else throw IndexOutOfBoundsException("size=32, start=$start, end=$end")
    }
}