package com.towertex.tmdbmodel.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.towertex.tmdbapi.TMDBApi
import com.towertex.tmdbapi.model.ConfigurationResponse
import com.towertex.tmdbmodel.model.Configuration
import com.towertex.tmdbmodel.room.TMDBDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.reflect.Type

class ConfigurationModel(
    private val api: TMDBApi,
    private val dao: TMDBDao
) : ConfigurationModelContract {

    companion object {
        private val type: Type = object : TypeToken<List<String?>?>() {}.type
    }

    override fun configurationGet(): Flow<Configuration?> = flow {
        val currentData = dao.getConfiguration()
        if (currentData.isNotEmpty()) {
            emit(currentData.first())
            return@flow
        }
        val newData = api.configurationGet().execute().body()
        if (newData == null) {
            emit(null)
            return@flow
        }
        emit(newData.toConfiguration())
    }

    private fun ConfigurationResponse.toConfiguration() : Configuration {
        val gson = Gson()
        return Configuration(
            baseUrl = images?.base_url,
            secureBaseUrl = images?.secure_base_url,
            backdropSizes = images?.backdrop_sizes?.let { gson.toJson(it, type) },
            logoSizes = images?.logo_sizes?.let { gson.toJson(it, type) },
            posterSizes = images?.poster_sizes?.let { gson.toJson(it, type) },
            profileSizes = images?.profile_sizes?.let { gson.toJson(it, type) },
            stillSizes = images?.still_sizes?.let { gson.toJson(it, type) },
            changeKeys = change_keys?.let { gson.toJson(it, type) },
        )
    }
}