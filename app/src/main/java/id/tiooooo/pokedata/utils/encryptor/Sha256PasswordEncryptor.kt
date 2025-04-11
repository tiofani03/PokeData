package id.tiooooo.pokedata.utils.encryptor

import java.security.MessageDigest

class Sha256PasswordEncryptor : PasswordEncryptor {
    override fun encrypt(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
