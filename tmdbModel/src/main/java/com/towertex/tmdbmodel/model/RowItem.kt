package com.towertex.tmdbmodel.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "row_items",
    indices = [
        Index("id"),
    ],
    primaryKeys = [
        "id"
    ]
)
data class RowItem(
    //artificial
    @ColumnInfo(name = "page")
    val page: Int,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @ColumnInfo(name = "genre_ids")
    val genreIds: String, //List<Int>
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "original_title")
    val originalTitle: String?,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Float,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float
)