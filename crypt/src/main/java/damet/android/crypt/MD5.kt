package damet.android.crypt

import java.security.MessageDigest
import java.util.*

fun md5_16(string: String) : String = md5(string, 8, 24)
fun md5_16_upper(string: String) : String = md5(string, 8, 24).toUpperCase(Locale.ENGLISH)

fun md5_32(string: String) : String = md5(string, 0, 32)
fun md5_32_upper(string: String) : String = md5(string, 0, 32).toUpperCase(Locale.ENGLISH)

fun md5(string: String, start: Int, end: Int) : String {
    if (start in 0..32 && end in 0..32)
        return StringBuffer().apply {
            for (b in MessageDigest.getInstance("MD5").apply { update(string.toByteArray()) }.digest())
                append(String.format("%02x", b.toInt() and 0xff))
        }.substring(start, end)
    else throw IndexOutOfBoundsException("size=32, start=$start, end=$end")
}