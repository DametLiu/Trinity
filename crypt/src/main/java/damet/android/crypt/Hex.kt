package damet.android.crypt

import java.util.*

infix fun ByteArray.hex(upper: Boolean) : String = tohex(this, upper)
infix fun String.hex(upper: Boolean) : String = tohex(toByteArray(), upper)

private fun tohex(bytes: ByteArray, upper: Boolean) : String {
    val buf = StringBuffer()
    for (b in bytes)
        buf.append(String.format("%02x", b.toInt() and 0xff))

    val result = buf.toString()
    return if(upper) result.toUpperCase(Locale.ENGLISH) else result
}