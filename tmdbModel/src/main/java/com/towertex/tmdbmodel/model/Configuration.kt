package com.towertex.tmdbmodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "configuration",
    indices = [
        Index("id"),
    ],
    primaryKeys = [
        "id"
    ]
)
data class Configuration(
    //artificial
    @ColumnInfo(name = "id")
    val id: Int = 1, //there will be always only one

    @ColumnInfo(name = "base_url")
    val baseUrl: String?,
    @ColumnInfo(name = "secure_base_url")
    val secureBaseUrl: String?,
    @ColumnInfo(name = "backdrop_sizes")
    val backdropSizes: String?, //List<String>?
    @ColumnInfo(name = "logo_sizes")
    val logoSizes: String?, //List<String>?
    @ColumnInfo(name = "poster_sizes")
    val posterSizes: String?, //List<String>?
    @ColumnInfo(name = "profile_sizes")
    val profileSizes: String?, //List<String>?
    @ColumnInfo(name = "still_sizes")
    val stillSizes: String?, //List<String>?
    @ColumnInfo(name = "change_keys")
    val changeKeys: String?, //List<String>?
)