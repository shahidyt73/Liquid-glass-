package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IconPackDao {
    @Query("SELECT * FROM custom_glass_icons ORDER BY timestamp DESC")
    fun getAllCustomIcons(): Flow<List<CustomGlassIcon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomIcon(icon: CustomGlassIcon): Long

    @Query("DELETE FROM custom_glass_icons WHERE id = :id")
    suspend fun deleteCustomIcon(id: Long)

    @Query("SELECT * FROM favorite_icons ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteIcon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteIcon)

    @Query("DELETE FROM favorite_icons WHERE iconName = :name")
    suspend fun removeFavorite(name: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_icons WHERE iconName = :name)")
    fun isFavorite(name: String): Flow<Boolean>
}
