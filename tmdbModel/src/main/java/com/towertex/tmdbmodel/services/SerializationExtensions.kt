package com.towertex.tmdbmodel.services

import kotlinx.serialization.json.Json

internal inline fun <reified T> List<T>.toJson(): String = Json.encodeToString(this)