package com.example.projecto_suarez.data.remote

import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.data.remote.dto.LabResponse
import retrofit2.http.GET

interface CloudApi {
    @GET("courses")
     fun getCourses(): List<LabResponse>

    @GET("labs")
     fun getLabs(): List<LabResponse>

    @GET("courses")
    fun enrolled(idStudent: String, idLab: String)

    @GET("labs")
    fun unregister(idStudent: String, idLab: String)

}