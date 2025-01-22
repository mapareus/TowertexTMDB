package com.towertex.tmdbapi

import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbapi.model.ConfigurationResponse
import com.towertex.tmdbapi.model.TrendingResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

//TODO split to @NESTED inner classes
class TMDBApiTest {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTk1NDA5ZWU2NDdjOTVlNjMyZDNiMzJlYzc3ODBjZSIsInN1YiI6IjYzMmMzN2Y5YzhmM2M0MDA4M2VhYmM1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dyUD9h6YUuIcRbx-pQY0HmCUc8xHd6qfBEcMDyFbd6I"
        private const val EXPECTED_PAGE_SIZE = 20

        private lateinit var api: TMDBApi
        private lateinit var wrongApi: TMDBApi
    }

    @Before
    fun init() {
        api = TMDBApiBuilder(BEARER_TOKEN, BASE_URL)
            .setLogAll()
            .setDebugLogger()
            .build()
        wrongApi = TMDBApiBuilder("wrong bearer", BASE_URL)
            .setLogInfo()
            .setLogHeaders()
            .setLogBody()
            .setLogNone()
            .setReleaseLogger()
            .build()
    }

    @Test
    fun `dummy test to verify the JUnit itself is working correctly`() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `configurationGet will fail when the authentication is missing`() = runBlocking {
        val result = wrongApi.configurationGet()
        assertNotNull("result should be Error", result)
        assertTrue("result should be Error", result is Error)
        assertEquals(TMDBApiErrorType.UNAUTHORIZED, (result as? Error)?.apiErrorType)
        assertEquals(401, (result as? Error)?.httpCode)
        assertEquals(7, (result as? Error)?.error?.status_code)
    }

    @Test
    fun `configurationGet returns valid result`() = runBlocking {
        val result = api.configurationGet()
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? ConfigurationResponse
        assertTrue("response should be ConfigurationResponse", response is ConfigurationResponse)
        val message = "response should not be empty"
        assertNotNull(message, response?.images?.base_url)
        assertNotNull(message, response?.images?.logo_sizes)
        assertNotNull(message, response?.images?.still_sizes)
        assertNotNull(message, response?.images?.poster_sizes)
        assertNotNull(message, response?.images?.profile_sizes)
        assertNotNull(message, response?.images?.backdrop_sizes)
        assertNotNull(message, response?.images?.secure_base_url)
        assertNotNull(message, response?.change_keys)
    }

    @Test
    fun `trendingGet will fail when the authentication is missing`() = runBlocking {
        val result = wrongApi.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK)
        assertNotNull("result should be Error", result)
        assertTrue("result should be Error", result is Error)
        assertEquals(TMDBApiErrorType.UNAUTHORIZED, (result as? Error)?.apiErrorType)
        assertEquals(401, (result as? Error)?.httpCode)
        assertEquals(7, (result as? Error)?.error?.status_code)
    }

    @Test
    fun `trendingGet returns first page by default`() = runBlocking {
        val result = api.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK)
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? TrendingResponse
        assertTrue("response should be TrendingResponse", response is TrendingResponse)
        assertEquals(EXPECTED_PAGE_SIZE, response?.results?.size ?: 0)
        assertEquals(1, response?.page)
    }

    @Test
    fun `trendingGet returns second page`() = runBlocking {
        val result = api.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK, 2)
        assertTrue("result should be Success", result is Success)
        val response = (result as? Success<*>)?.data as? TrendingResponse
        assertTrue("response should be TrendingResponse", response is TrendingResponse)
        assertTrue("response page should not be empty", response?.results.isNullOrEmpty().not())
        assertEquals(2, response?.page)
    }

    @Test
    fun `trendingGet returns valid result`() = runBlocking {
        val result = api.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK)
        val response = (result as? Success<*>)?.data as? TrendingResponse
        val message = "response should not be empty"
        assertTrue(message, response?.results.isNullOrEmpty().not())
        assertNotNull(message, response?.page)
        assertNotNull(message, response?.total_pages)
        assertNotNull(message, response?.total_results)
        assertTrue(message, response!!.results.any { it.original_language.isNotEmpty() })
        assertTrue(message, response.results.any { it.title.isNullOrEmpty().not() })
        assertTrue(message, response.results.any { it.backdrop_path.isNullOrEmpty().not() })
        assertTrue(message, response.results.any { it.genre_ids.isNotEmpty() })
        assertTrue(message, response.results.any { it.poster_path.isNullOrEmpty().not() })
    }

    @Test
    fun `api UnresolvedAddressException should return NO_INTERNET`(): Unit = runBlocking {
        val result = HttpClient(Android).toResult<String> { throw UnresolvedAddressException() }
        assertTrue("result should be Exception", result is Exception)
        assertEquals(TMDBApiErrorType.NO_INTERNET, (result as? Exception)?.apiErrorType)
    }

    @Test
    fun `api SerializationException should return SERIALIZATION`(): Unit = runBlocking {
        val result = HttpClient(Android).toResult<String> { throw SerializationException() }
        assertTrue("result should be Exception", result is Exception)
        assertEquals(TMDBApiErrorType.SERIALIZATION, (result as? Exception)?.apiErrorType)
    }
}