package com.towertex.tmdb.adapters

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test

class BindingAdaptersTest {

    private lateinit var imageView: ImageView
    private lateinit var glide: RequestManager

    @Before
    fun setUp() {
        imageView = mockk(relaxed = true)
        glide = mockk(relaxed = true)
        mockkStatic(Glide::class)
        every { Glide.with(imageView) } returns glide
    }

    @Test
    fun `test loadImage with url string`() {
        val url = "https://example.com/image.jpg"
        loadImage(imageView, url)
        verify { glide.load(url).into(imageView) }
    }

    @Test
    fun `test loadImage with empty url string`() {
        val url = ""
        loadImage(imageView, url)
        verify(exactly = 0) { glide.load(any<String>()).into(any()) }
    }

    @Test
    fun `test loadImage with StateFlow url`() {
        val url = MutableStateFlow("https://example.com/image.jpg")
        loadImage(imageView, url)
        verify { glide.load(url).into(imageView) }
    }

    @Test
    fun `test loadImage with empty StateFlow url`() {
        val url = MutableStateFlow("")
        loadImage(imageView, url)
        verify(exactly = 0) { glide.load(any<String>()).into(any()) }
    }
}