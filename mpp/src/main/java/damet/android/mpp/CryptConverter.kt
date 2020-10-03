package damet.android.mpp

import damet.android.crypt.AES

internal object CryptConverter {
    fun encode(obj : String, pwd: String) : String =
        if (pwd.isEmpty()) obj else AES.encrptWithRandomIV(obj, pwd)

    fun decode(str : String, pwd: String) : String =
        if (pwd.isEmpty()) str else AES.decryptWithRandomIV(str, pwd)
}