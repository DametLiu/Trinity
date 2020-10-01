package damet.android.crypt

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.Cipher.DECRYPT_MODE
import javax.crypto.Cipher.ENCRYPT_MODE
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES {
    enum class CryptMode { ENCRYPT, DECRYPT }

    private val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

    fun encrypt(str: String, pwd: String, iv: String): String =
        Base64.encodeToString(crypty(str, pwd.sha256().substring(0, 32), CryptMode.ENCRYPT, iv), Base64.DEFAULT)

    fun decrypt(str: String, pwd: String, iv: String): String =
        String(crypty(str, pwd.sha256().substring(0, 32), CryptMode.DECRYPT, iv))

    private fun crypty(str: String, pwd: String, mode: CryptMode, iv: String) : ByteArray {
        val len = 32.coerceAtLeast(pwd.toByteArray().size)
        val ivlen = 16.coerceAtMost(iv.toByteArray().size)

        val keySpec = SecretKeySpec(pwd.toByteArray().sub(0, len), "AES")
        val ivSpec = IvParameterSpec(iv.toByteArray().sub(0, ivlen))

        return if (mode == CryptMode.ENCRYPT) cipher.run {
            init(ENCRYPT_MODE, keySpec, ivSpec)
            doFinal(str.toByteArray())
        } else cipher.run {
            init(DECRYPT_MODE, keySpec, ivSpec)
            doFinal(Base64.decode(str.toByteArray(), Base64.DEFAULT))
        }
    }

    fun encrptWithRandomIV(str: String, pwd: String) : String =
        Base64.encodeToString(crypty("${generateRandomIV16()}$str", pwd.sha256().substring(0, 32), CryptMode.ENCRYPT, generateRandomIV16()), Base64.DEFAULT)

    fun decryptWithRandomIV(str: String, pwd: String): String =
        String(crypty(str, pwd.sha256().substring(0, 32), CryptMode.DECRYPT, generateRandomIV16())).substring(16)

    private fun generateRandomIV16() : String {
        val ranGen = SecureRandom()
        val aesKey = ByteArray(16).apply { ranGen.nextBytes(this) }
        val result = aesKey hex false
        return result.substring(0, 16.coerceAtMost(result.length))
    }

    private fun ByteArray.sub(start: Int, len: Int) : ByteArray = ByteArray(len).apply {
        System.arraycopy( this@sub, start, this, 0, len )
    }
}
