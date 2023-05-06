package com.bntech.bnauth.service

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.JWTVerifier
import com.bntech.bnauth.service.impl.JwtServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.ResourceLoader
import org.springframework.security.core.userdetails.UserDetails
import java.time.Instant

class JwtServiceImplTest {
    private lateinit var jwtServiceImpl: JwtServiceImpl
    private lateinit var userDetailsMock: UserDetails

    @BeforeEach
    fun setup() {
        val resourceLoader: ResourceLoader = DefaultResourceLoader()
        jwtServiceImpl = JwtServiceImpl(resourceLoader)
        userDetailsMock = Mockito.mock(UserDetails::class.java)
    }

    @Test
    fun `should generate valid jwt token`() {
        val username = "testUser"
        Mockito.`when`(userDetailsMock.username).thenReturn(username)

        val token = jwtServiceImpl.keygen(userDetailsMock)

        assertNotNull(token)

        val verifier: JWTVerifier = jwtServiceImpl.getCheck()
        val decodedJWT = verifier.verify(token)

        assertEquals(username, decodedJWT.subject)
        assertEquals("app", decodedJWT.issuer)
        assertTrue(decodedJWT.issuedAt.toInstant().isBefore(Instant.now()))
        assertTrue(decodedJWT.expiresAt.toInstant().isAfter(Instant.now()))
    }

    @Test
    fun `should throw exception when token is invalid`() {
        val invalidToken = "invalidToken"

        assertThrows<JWTVerificationException> { jwtServiceImpl.validate(invalidToken) }
    }

    @Test
    fun `should return username when token is valid`() {
        val username = "testUser"
        val instant = Instant.now()
        val token = JWT.create()
            .withSubject(username)
            .withIssuer("app")
            .withIssuedAt(instant)
            .withExpiresAt(instant.plusSeconds(60))
            .sign(jwtServiceImpl.getHmac512())

        val validatedUsername = jwtServiceImpl.validate(token)

        assertEquals(username, validatedUsername)
    }

    @Test
    fun `should throw exception when token is expired`() {
        val username = "user"
        val instant = Instant.now().minusSeconds(60)

        val token = JWT.create()
            .withSubject(username)
            .withIssuer("app")
            .withIssuedAt(instant)
            .withExpiresAt(instant.plusSeconds(1))
            .sign(jwtServiceImpl.getHmac512())

        assertThrows<JWTVerificationException> { jwtServiceImpl.validate(token) }
    }
}
