package com.towertex.tmdbmodel.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.towertex.tmdbmodel.model.Configuration
import com.towertex.tmdbmodel.model.RowItem

@Dao
interface TMDBDao {
    @Query("SELECT * FROM row_items WHERE page = :page")
    suspend fun getPage(page: Int): List<RowItem>

    @Query("SELECT * FROM row_items")
    suspend fun getAll(): List<RowItem>

    @Query("DELETE FROM row_items WHERE id IN (:ids)")
    suspend fun deleteItems(ids: List<Int>)

    @Query("DELETE FROM row_items")
    suspend fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<RowItem>)

    @Query("SELECT * FROM row_items WHERE id = :id")
    suspend fun getItem(id: Int): List<RowItem>



    @Query("SELECT * FROM configuration WHERE id = 1")
    suspend fun getConfiguration(): List<Configuration>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfiguration(configuration: Configuration)
}