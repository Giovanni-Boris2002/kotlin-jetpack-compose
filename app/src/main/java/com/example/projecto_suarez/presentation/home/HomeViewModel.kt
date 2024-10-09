package com.example.projecto_suarez.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.projecto_suarez.data.remote.dto.GeneralResponse
import com.example.projecto_suarez.domain.usescases.cloud.ApiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import com.example.projecto_suarez.data.remote.dto.LabResponse

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases
): ViewModel() {
    private val _list = mutableStateOf<List<LabResponse>>(emptyList())
    val list: State<List<LabResponse>> = _list

    fun getCourses() {
        viewModelScope.launch {
            try {
                val listData = apiUseCases.getCourses()
                _list.value = listData
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

    fun getLabs() {
        viewModelScope.launch {
            try {
                val listData = apiUseCases.getLabs()
                _list.value = listData
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
}