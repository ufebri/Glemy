package com.raytalktech.gleamy.model

data class Error(val status_code: Int = 0,
                 val status_message: String? = null)