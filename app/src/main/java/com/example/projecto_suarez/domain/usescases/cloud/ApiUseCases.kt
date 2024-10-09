package com.example.projecto_suarez.domain.usescases.cloud

data class ApiUseCases(
    val getCourses: GetCourses,
    val getLabs: GetLabs,
    val enrolledLab: EnrolledLab,
    val unregisterLab: UnregisterLab
)
