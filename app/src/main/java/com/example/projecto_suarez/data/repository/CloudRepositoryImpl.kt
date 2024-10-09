package com.example.projecto_suarez.data.repository


import com.example.projecto_suarez.data.remote.CloudApi
import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.data.remote.dto.LabResponse
import com.example.projecto_suarez.domain.repository.CloudRepository
import retrofit2.http.GET


class CloudRepositoryImpl(
    private  val newsApi: CloudApi

): CloudRepository {
    override suspend fun getCourses(): List<LabResponse> {
        return newsApi.getCourses();
    }
    override suspend fun getLabs(): List<LabResponse> {
        return newsApi.getLabs();
    }
    override suspend fun enrolled(idStudent: String, idLab: String){
        return newsApi.enrolled(idStudent, idLab)
    }

    override suspend fun unregister(idStudent: String, idLab: String){
        return newsApi.unregister(idStudent, idLab)
    }
}