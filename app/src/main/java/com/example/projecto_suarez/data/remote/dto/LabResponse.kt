package com.example.projecto_suarez.data.remote.dto

data class LabResponse (
    val title: String,
    val urlToImage: String,
    val name: String,
    val students: List<StudentResponse>,
    val desc: String,
    val id: String
)
