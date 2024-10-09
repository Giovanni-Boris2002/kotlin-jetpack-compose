package com.example.projecto_suarez.domain.usescases.cloud

import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.domain.repository.CloudRepository

class EnrolledLab(
    private val cloudRepository: CloudRepository
) {
    suspend operator fun invoke(idStudent: String, idLab: String): Unit {
        return cloudRepository.enrolled(idStudent, idLab);
    }
}