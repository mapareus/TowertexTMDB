package com.towertex.tmdbapi

import com.towertex.tmdbapi.data.TMDBApiException
import com.towertex.tmdbapi.services.Trending
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class JupiterApiTest {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTk1NDA5ZWU2NDdjOTVlNjMyZDNiMzJlYzc3ODBjZSIsInN1YiI6IjYzMmMzN2Y5YzhmM2M0MDA4M2VhYmM1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dyUD9h6YUuIcRbx-pQY0HmCUc8xHd6qfBEcMDyFbd6I"
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testTrending(): Unit = runBlocking {
        val api = TMDBApiBuilder(BASE_URL).build()

        try {
            val response = api.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK, BEARER_TOKEN).execute()
            assertTrue("response not success", response.isSuccessful)
            assertTrue("response empty", (response.body()?.results?.size ?: 0) > 0)
            assertEquals(1, response.body()?.page)
        } catch (e: Throwable) {
            fail("should not fail 1")
        }

        try {
            val response = api.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK, BEARER_TOKEN, 2).execute()
            assertTrue("response not success", response.isSuccessful)
            assertTrue("response empty", (response.body()?.results?.size ?: 0) > 0)
            assertEquals(2, response.body()?.page)
        } catch (e: Throwable) {
            fail("should not fail 2")
        }

        try {
            api.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK, "wrong bearer").execute()
            fail("should have failed")
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(401, (e as TMDBApiException).httpCode)
            assertEquals(7, (e as TMDBApiException).errorBody?.status_code)
        }

        try {
            api.trendingGet("wrong_type", Trending.TIME_WINDOW_WEEK, BEARER_TOKEN).execute()
            fail("should have failed")
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(400, (e as TMDBApiException).httpCode)
            assertEquals(5, (e as TMDBApiException).errorBody?.status_code)
        }

        try {
            api.trendingGet(Trending.MEDIA_TYPE_ALL,"wrong_window", BEARER_TOKEN).execute()
            fail("should have failed")
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(400, (e as TMDBApiException).httpCode)
            assertEquals(5, (e as TMDBApiException).errorBody?.status_code)
        }
    }

    @Test
    fun testConfiguration(): Unit = runBlocking {
        val api = TMDBApiBuilder(BASE_URL).build()

        try {
            val response = api.configurationGet(BEARER_TOKEN).execute()
            assertTrue("response not success", response.isSuccessful)
            assertTrue("response empty", response.body()?.images != null)
        } catch (e: Throwable) {
            fail("should not fail 1")
        }


        try {
            api.configurationGet("wrong bearer").execute()
            fail("should have failed")
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(401, (e as TMDBApiException).httpCode)
            assertEquals(7, (e as TMDBApiException).errorBody?.status_code)
        }
    }
}