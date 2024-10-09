package com.example.projecto_suarez.domain.repository

import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.data.remote.dto.LabResponse


interface CloudRepository {
     suspend fun getCourses(): List<LabResponse>
     suspend fun getLabs(): List<LabResponse>
     suspend fun enrolled(idStudent: String, idLab: String)
     suspend fun unregister(idStudent: String, idLab: String)
}