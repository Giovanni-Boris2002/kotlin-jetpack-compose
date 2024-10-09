package com.example.projecto_suarez.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecto_suarez.domain.usescases.cloud.ApiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases
): ViewModel(){

    fun onEvent(event: DetailsEvent){
        when(event){
            is DetailsEvent.enrollStudent -> {
                viewModelScope.launch {
                    apiUseCases.enrolledLab(event.idStudent, event.idLab)
                    event.navigate()
                }
            }

            is DetailsEvent.deregisterStudent -> {
                viewModelScope.launch {
                    apiUseCases.unregisterLab(event.idStudent, event.idLab)
                    event.navigate()
                }
            }
        }
    }

}