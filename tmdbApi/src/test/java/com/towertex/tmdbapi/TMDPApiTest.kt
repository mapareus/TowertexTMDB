package com.towertex.tmdbapi

import com.towertex.tmdbapi.model.TMDBApiException
import com.towertex.tmdbapi.services.Trending
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.AssertionError

class TMDPApiTest {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTk1NDA5ZWU2NDdjOTVlNjMyZDNiMzJlYzc3ODBjZSIsInN1YiI6IjYzMmMzN2Y5YzhmM2M0MDA4M2VhYmM1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dyUD9h6YUuIcRbx-pQY0HmCUc8xHd6qfBEcMDyFbd6I"
        private const val EXPECTED_PAGE_SIZE = 20

        private lateinit var api: TMDBApi
        private lateinit var wrongApi: TMDBApi
    }

    @Test
    fun dummyTest() {
        assertEquals(4, 2 + 2)
    }

    @Before
    fun init() {
        api = TMDBApiBuilder(BASE_URL, BEARER_TOKEN).build()
        wrongApi = TMDBApiBuilder(BASE_URL, "wrong bearer").build()
    }

    @Test
    fun testTrending(): Unit = runBlocking {
        try {
            val response = api.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK).execute()
            assertTrue("response not success", response.isSuccessful)
            assertEquals(EXPECTED_PAGE_SIZE, response.body()?.results?.size ?: 0)
            assertEquals(1, response.body()?.page)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail 1 $e")
        }

        try {
            val response = api.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK, 2).execute()
            assertTrue("response not success", response.isSuccessful)
            assertTrue("response empty", (response.body()?.results?.size ?: 0) > 0)
            assertEquals(2, response.body()?.page)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail 2")
        }

        try {
            wrongApi.trendingGet(Trending.MEDIA_TYPE_ALL, Trending.TIME_WINDOW_WEEK).execute()
            fail("should have failed")
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(401, (e as TMDBApiException).httpCode)
            assertEquals(7, (e as TMDBApiException).errorBody?.status_code)
        }

        try {
            api.trendingGet("wrong_type", Trending.TIME_WINDOW_WEEK).execute()
            fail("should have failed")
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(400, (e as TMDBApiException).httpCode)
            assertEquals(5, (e as TMDBApiException).errorBody?.status_code)
        }

        try {
            api.trendingGet(Trending.MEDIA_TYPE_ALL,"wrong_window").execute()
            fail("should have failed")
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(400, (e as TMDBApiException).httpCode)
            assertEquals(5, (e as TMDBApiException).errorBody?.status_code)
        }
    }

    @Test
    fun testConfiguration(): Unit = runBlocking {
        try {
            val response = api.configurationGet().execute()
            assertTrue("response not success", response.isSuccessful)
            assertTrue("response empty", response.body()?.images != null)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail 1")
        }

        try {
            wrongApi.configurationGet().execute()
            fail("should have failed")
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            assertTrue("should be TMDBApiException", e is TMDBApiException)
            assertEquals(401, (e as TMDBApiException).httpCode)
            assertEquals(7, (e as TMDBApiException).errorBody?.status_code)
        }
    }
}