package com.carlostorres.poststest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Post(
        val userId: Int,
        @field:PrimaryKey
        val id: Int,
        val title: String,
        val body: String,
        var read: Boolean = true,
        var favorite: Boolean = false
): Serializable