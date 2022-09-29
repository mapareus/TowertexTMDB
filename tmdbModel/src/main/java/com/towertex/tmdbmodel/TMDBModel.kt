package com.towertex.tmdbmodel

import com.towertex.tmdbapi.TMDBApi
import com.towertex.tmdbmodel.room.TMDBDatabase
import com.towertex.tmdbmodel.services.ConfigurationModel
import com.towertex.tmdbmodel.services.ConfigurationModelContract
import com.towertex.tmdbmodel.services.TrendingModel
import com.towertex.tmdbmodel.services.TrendingModelContract

//data model is divided into sections
//each section is implemented via dedicated delegate
//data model is using SingleSourceOfTruth architecture where the app gets only Room objects but never the Retrofit objects
//in the Demo app there are only two parts of the model and each has only one service
class TMDBModel(api: TMDBApi, db: TMDBDatabase) :
        TrendingModelContract by TrendingModel(api, db.tmdbDao),
        ConfigurationModelContract by ConfigurationModel(api, db.tmdbDao)