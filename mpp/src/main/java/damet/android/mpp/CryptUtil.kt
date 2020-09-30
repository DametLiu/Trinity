package damet.android.mpp

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object CryptUtil {
    private fun md5(string : String) : String {
        return StringBuffer().apply {
            for (b in MessageDigest.getInstance("MD5").apply { update(string.toByteArray(Charsets.UTF_8)) }.digest())
                append(String.format("%02x", b and 0xff))
        }.substring(8, 24)
    }

    @SuppressLint("GetInstance")
    fun encrypt(str: String, pwd: String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec? = SecretKeySpec(md5(pwd).toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val enCryptRes = cipher.doFinal(str.toByteArray())
        return String(Base64.encode(enCryptRes, Base64.NO_WRAP))
    }

    @SuppressLint("GetInstance")
    fun decrypt(str: String, pwd: String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec? = SecretKeySpec(md5(pwd).toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val deCryptRes = cipher.doFinal(Base64.decode(str, Base64.NO_WRAP))
        return String(deCryptRes)
    }

    private infix fun Byte.and(mask: Int): Int = toInt() and mask
}
