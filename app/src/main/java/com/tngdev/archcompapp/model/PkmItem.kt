package com.tngdev.archcompapp.model

import androidx.room.Entity

@Entity
data class PkmItem (
    val name: String?,
    val url: String?
)