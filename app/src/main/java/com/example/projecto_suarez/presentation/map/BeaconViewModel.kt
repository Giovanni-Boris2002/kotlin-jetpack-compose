package com.example.projecto_suarez.presentation.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projecto_suarez.domain.usescases.news.NewsUseCases
import com.example.projecto_suarez.presentation.search.SearchEvent
import com.example.projecto_suarez.services.BeaconScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class BeaconViewModel @Inject constructor(
    private val beaconScanner: BeaconScanner,
    private val newsUseCases: NewsUseCases
): ViewModel(){
    val _state = mutableStateOf(MapState());
    val state : State<MapState> = _state;

    init {
        beaconScanner.initBluetooth()
        beaconScanner.bluetoothScanStart(beaconScanner.bleScanCallback)
    }
    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.RedirectoToDetails -> {
                viewModelScope.launch {
                    val article = newsUseCases.searchNewById(
                        sources = listOf("bbc-news", "abc-news", "al-jazeera-english"),
                        id = event.qrResult
                    )
                    event.navigateToDetails(article.articles[0])
                }
            }

        }
    }
    companion object {
        val TAG = "BeaconViewModel"
    }
}