@file:Suppress("UNCHECKED_CAST", "NAME_SHADOWING")

package damet.android.mpp

import damet.android.crypt.AES

internal object CryptConverter {
    fun encode(obj : String, pwd: String) : String {
        return if (pwd.isEmpty()) obj else AES.encrptWithRandomIV(obj, pwd)
    }

    fun decode(str : String, default: String, pwd: String) : String {
        return if (pwd.isEmpty()) str else AES.decryptWithRandomIV(str, pwd)
    }
}