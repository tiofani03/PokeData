package id.tiooooo.pokedata.utils.encryptor

interface PasswordEncryptor {
    fun encrypt(password: String): String
}
