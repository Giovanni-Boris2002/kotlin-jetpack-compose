package com.example.projecto_suarez.domain.usescases.app_entry

import com.example.projecto_suarez.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }
}