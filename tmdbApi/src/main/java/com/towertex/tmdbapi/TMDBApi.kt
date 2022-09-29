package com.towertex.tmdbapi

import com.towertex.tmdbapi.services.Configuration
import com.towertex.tmdbapi.services.ConfigurationApi
import com.towertex.tmdbapi.services.Trending
import com.towertex.tmdbapi.services.TrendingApi
import retrofit2.Retrofit

//server api is typically divided into groups of services, here each group is implemented in separate delegate
class TMDBApi internal constructor(retrofit: Retrofit, token: String) :
    Trending by TrendingApi(retrofit, token),
    Configuration by ConfigurationApi(retrofit, token)