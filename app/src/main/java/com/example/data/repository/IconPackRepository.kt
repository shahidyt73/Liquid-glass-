package com.example.data.repository

import com.example.data.local.CustomGlassIcon
import com.example.data.local.FavoriteIcon
import com.example.data.local.IconPackDao
import kotlinx.coroutines.flow.Flow

class IconPackRepository(private val dao: IconPackDao) {
    val allCustomIcons: Flow<List<CustomGlassIcon>> = dao.getAllCustomIcons()
    val allFavorites: Flow<List<FavoriteIcon>> = dao.getAllFavorites()

    suspend fun saveCustomIcon(icon: CustomGlassIcon): Long = dao.insertCustomIcon(icon)
    suspend fun deleteCustomIcon(id: Long) = dao.deleteCustomIcon(id)

    suspend fun toggleFavorite(iconName: String, isFav: Boolean) {
        if (isFav) {
            dao.removeFavorite(iconName)
        } else {
            dao.addFavorite(FavoriteIcon(iconName))
        }
    }

    fun isFavorite(iconName: String): Flow<Boolean> = dao.isFavorite(iconName)
}
