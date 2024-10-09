package com.example.projecto_suarez.domain.usescases.cloud

import com.example.projecto_suarez.domain.repository.CloudRepository

class UnregisterLab(
    private val cloudRepository: CloudRepository
) {
    suspend operator fun invoke(idStudent: String, idLab: String): Unit {
        return cloudRepository.unregister(idStudent, idLab);
    }
}