package damet.android.mpp

import android.annotation.SuppressLint
import android.util.Base64
import damet.android.crypt.md5_16_upper
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

internal object CryptUtil {
    @SuppressLint("GetInstance")
    fun encrypt(str: String, pwd: String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec? = SecretKeySpec(md5_16_upper(pwd).toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val enCryptRes = cipher.doFinal(str.toByteArray())
        return String(Base64.encode(enCryptRes, Base64.NO_WRAP))
    }

    @SuppressLint("GetInstance")
    fun decrypt(str: String, pwd: String): String {
        val cipher = Cipher.getInstance("AES")
        val secretKeySpec: SecretKeySpec? = SecretKeySpec(md5_16_upper(pwd).toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val deCryptRes = cipher.doFinal(Base64.decode(str, Base64.NO_WRAP))
        return String(deCryptRes)
    }
}
