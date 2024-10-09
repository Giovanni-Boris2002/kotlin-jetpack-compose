package com.example.projecto_suarez.presentation.details

sealed class DetailsEvent {
    data class enrollStudent(val idStudent: String, val idLab: String, val navigate: () -> Unit): DetailsEvent()
    data class deregisterStudent(val idStudent: String, val idLab: String, val navigate: () -> Unit): DetailsEvent()
}