package ru.somarov.berte.auth.config.properties.dto.security

import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.security.KeyPair
import java.security.KeyStore
import java.security.PrivateKey
import java.time.Duration


data class JwtProps(
    val keystore: KeystoreProps,
    val alias: String,
    val alg: String,
    val accessExpiration: Duration,
    val refreshExpiration: Duration,
) {
    val keys: KeyPair

    init {
        val stream = FileInputStream(ResourceUtils.getFile(keystore.url))
        val ks = KeyStore.getInstance(KeyStore.getDefaultType())
        val passwd = keystore.password.toCharArray()
        ks.load(stream, passwd)

        keys = KeyPair(ks.getCertificate(alias).publicKey, ks.getKey(alias, passwd) as PrivateKey)
    }
}
