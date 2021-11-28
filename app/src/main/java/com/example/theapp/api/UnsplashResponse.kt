package com.example.theapp.api

import com.example.theapp.model.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)
