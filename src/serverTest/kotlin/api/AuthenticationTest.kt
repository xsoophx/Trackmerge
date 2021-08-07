package api

import api.authentication.createAuthUrl
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationTest {

    @Test
    fun `correct redirect URI is created`() {
        val expected = "https://accounts.spotify.com/authorize?client_id=57b8759dc45246eb9e1434cedbaf5e44&" +
            "response_type=token&redirect_uri=https%3A%2F%2Fsuffro.cc%2Ftrackmerge%2Fcallback" +
            "&show_dialog=true&scope="
        assertEquals(expected = expected, actual = createAuthUrl())
    }
}
