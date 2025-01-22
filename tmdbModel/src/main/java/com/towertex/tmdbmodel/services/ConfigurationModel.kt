package com.towertex.tmdbmodel.services

import com.towertex.tmdbapi.Success
import com.towertex.tmdbapi.TMDBApi
import com.towertex.tmdbapi.model.ConfigurationResponse
import com.towertex.tmdbmodel.model.Configuration
import com.towertex.tmdbmodel.room.TMDBDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ConfigurationModel(
    private val api: TMDBApi,
    private val dao: TMDBDao
) : ConfigurationModelContract {

    override fun configurationGet(): Flow<Configuration?> = flow {
        val currentData = dao.getConfiguration()
        if (currentData.isNotEmpty()) {
            emit(currentData.first())
            return@flow
        }
        val newData = (api.configurationGet() as? Success<ConfigurationResponse>)?.data
        if (newData == null) {
            emit(null)
            return@flow
        }
        emit(newData.toConfiguration())
    }

    private fun ConfigurationResponse.toConfiguration(): Configuration = with(images) {
        Configuration(
            baseUrl = base_url,
            secureBaseUrl = secure_base_url,
            backdropSizes = backdrop_sizes.toJson(),
            logoSizes = logo_sizes.toJson(),
            posterSizes = poster_sizes.toJson(),
            profileSizes = profile_sizes.toJson(),
            stillSizes = still_sizes.toJson(),
            changeKeys = change_keys.toJson(),
        )
    }
}