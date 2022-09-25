package com.towertex.tmdbapi

import com.towertex.tmdbapi.services.Configuration
import com.towertex.tmdbapi.services.ConfigurationApi
import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbapi.services.TrendingApi
import retrofit2.Retrofit

class TMDBApi(retrofit: Retrofit) :
    Trending by TrendingApi(retrofit),
    Configuration by ConfigurationApi(retrofit)