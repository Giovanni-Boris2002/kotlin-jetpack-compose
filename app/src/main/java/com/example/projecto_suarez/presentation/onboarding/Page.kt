package com.example.projecto_suarez.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.projecto_suarez.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
val pages = listOf(
    Page(
        title = "Explore Unique Art",
        description = "Immerse yourself in a world of creativity and expression. Discover unique works by renowned and emerging artists.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Create Your Own Story",
        description = "Make each visit a personal experience. Find works that inspire you and tell your story. An amazin place to find art lovers",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Share and Connect",
        description = "Celebrate art by sharing your discoveries with friends and family. Join a passionate community of art lovers.",
        image = R.drawable.onboarding3
    )
)