package com.example.projecto_suarez.domain.usescases.cloud

import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.data.remote.dto.LabResponse
import com.example.projecto_suarez.domain.repository.CloudRepository

class GetCourses(
    private val cloudRepository: CloudRepository
) {
    suspend operator fun invoke(): List<LabResponse> {
        return cloudRepository.getCourses();
    }
}