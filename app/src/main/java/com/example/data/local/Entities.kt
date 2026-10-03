package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_glass_icons")
data class CustomGlassIcon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val glyphName: String,
    val styleName: String,
    val shapeName: String,
    val opacity: Float,
    val refraction: Float,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorite_icons")
data class FavoriteIcon(
    @PrimaryKey
    val iconName: String,
    val addedAt: Long = System.currentTimeMillis()
)
