package com.towertex.tmdbmodel

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.towertex.tmdbapi.TMDBApi
import com.towertex.tmdbapi.TMDBApiBuilder
import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbmodel.room.TMDBDatabase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TMDBModelTest {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOTk1NDA5ZWU2NDdjOTVlNjMyZDNiMzJlYzc3ODBjZSIsInN1YiI6IjYzMmMzN2Y5YzhmM2M0MDA4M2VhYmM1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.dyUD9h6YUuIcRbx-pQY0HmCUc8xHd6qfBEcMDyFbd6I"
        private const val EXPECTED_PAGE_SIZE = 20

        private lateinit var appContext: Context
        private lateinit var api: TMDBApi
        private lateinit var db: TMDBDatabase
        private lateinit var model: TMDBModel
    }

    @Test
    fun dummyTest1() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dummyTest2() {
        //Context of the app under test.
        assertEquals("com.towertex.tmdbmodel.test", appContext.packageName)
    }

    @Before
    fun init(): Unit = runBlocking {
        api = TMDBApiBuilder(BEARER_TOKEN, BASE_URL)
            .setLogAll()
            .setDebugLogger()
            .build()
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = TMDBDatabase.buildDatabase(appContext)
        model = TMDBModel(api, db)
    }

    @After
    fun finally(): Unit = runBlocking {
        db.tmdbDao.deleteAllItems()
        db.close()
    }

    @Test
    fun testConfiguration(): Unit = runBlocking {
        try {
            //first we should get the response from network
            var i = 0
            model.configurationGet().onEach{
                i++
                assertNotNull(it)
                assertEquals(1, i)
                assertEquals(1, it?.id)
            }.launchIn(this).join()

            //check whether the data are indeed in the database
            val db = db.tmdbDao.getConfiguration()
            assertNotNull(db)

            //we should obtain the data from database
            i = 0
            model.configurationGet().onEach{
                i++
                assertNotNull(it)
                assertEquals(1, i)
                assertEquals(1, it?.id)
            }.launchIn(this)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail $e")
        }
    }

    @Test
    fun testTrending(): Unit = runBlocking {
        try {
            //first we should get just one response from network
            var i = 0
            model.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK, 1).onEach{
                i++
                assertEquals(EXPECTED_PAGE_SIZE, it.size)
                assertEquals(1, i)
                assertEquals(1, it.first().page)
            }.launchIn(this).join()

            //check whether the data are indeed in the database
            val dbItems = db.tmdbDao.getPage(1)
            assertEquals(EXPECTED_PAGE_SIZE, dbItems.size)
            assertEquals(1, dbItems.first().page)

            //artificially delete one item from db
            db.tmdbDao.deleteItems(listOf(dbItems.first().id))

            //we should obtain first the incomplete list from database and second should be the updated list from network
            i = 0
            model.trendingGet(Trending.MediaType.ALL, Trending.TimeWindow.WEEK, 1).onEach{
                i++
                when (i) {
                    1 -> assertEquals(EXPECTED_PAGE_SIZE-1, it.size)
                    2 -> assertEquals(EXPECTED_PAGE_SIZE, it.size)
                    else -> fail("there should be just two values")
                }
                assertEquals(1, it.first().page)
            }.launchIn(this)
        } catch (e: AssertionError) {
            throw e
        } catch (e: Throwable) {
            fail("should not fail $e")
        }
    }
}