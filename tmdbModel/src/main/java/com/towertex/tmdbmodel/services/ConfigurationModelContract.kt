package com.towertex.tmdbmodel.services

import com.towertex.tmdbmodel.model.Configuration
import kotlinx.coroutines.flow.Flow

interface ConfigurationModelContract {
    fun configurationGet(): Flow<Configuration?>
}