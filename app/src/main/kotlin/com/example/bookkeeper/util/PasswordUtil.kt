package com.example.bookkeeper.util

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtil {
    private const val ITERATIONS = 10000    // 哈希迭代次数
    private const val KEY_LENGTH = 256      // 哈希长度
    private const val SALT_LENGTH = 16      // 盐值长度

    /**
     * 生成盐值
     */
    fun generateSalt(): ByteArray {
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)
        return salt
    }

    // 生成密码哈希
    fun hashPassword(password: String, salt: ByteArray): String {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return toHexString(hash)
    }

    // 验证密码
    fun verifyPassword(inputPassword: String, storeHash: String, salt: ByteArray): Boolean {
        val inputHash = hashPassword(inputPassword, salt)
        return inputHash == storeHash
    }

    // ByteArray 转 16 进制字符串
    fun toHexString(b: ByteArray): String {
        return b.joinToString("") { "%02x".format(it) }
    }

    // 16 进制字符串转 ByteArray
    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            data[i / 2] = ((Character.digit(s[i], 16) shl 4) + Character.digit(s[i + 1], 16)).toByte()
        }
        return data
    }
}