package com.tngdev.archcompapp.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Sprite (
    @SerializedName("back_default")
    val backDefault : String?,
    @SerializedName("back_female")
    val backFemale : String?,
    @SerializedName("back_shiny")
    val backShiny : String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale : String?,
    @SerializedName("front_default")
    val frontDefault : String?,
    @SerializedName("front_female")
    val frontFemale : String?,
    @SerializedName("front_shiny")
    val frontShiny : String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale : String?
)