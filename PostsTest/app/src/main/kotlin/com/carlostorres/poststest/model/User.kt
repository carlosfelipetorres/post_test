package com.carlostorres.poststest.model

import java.io.Serializable

data class User(
        val id: Int? = 0,
        val name: String? = "",
        val phone: String? = "",
        val website: String? = "",
        val email: String? = ""
): Serializable