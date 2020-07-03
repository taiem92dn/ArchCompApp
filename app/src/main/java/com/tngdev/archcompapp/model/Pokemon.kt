package com.tngdev.archcompapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Pokemon (
    @PrimaryKey val id: Int,
    @SerializedName("base_experience")
    val baseExperience: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int,
    @Embedded val sprites: Sprite?,
    @SerializedName("weight")
    val weight: Int

)