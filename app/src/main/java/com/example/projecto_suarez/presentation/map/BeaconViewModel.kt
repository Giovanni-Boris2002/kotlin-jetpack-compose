package com.example.projecto_suarez.presentation.map

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.projecto_suarez.domain.usescases.news.NewsUseCases
import org.altbeacon.beacon.*
import com.example.projecto_suarez.services.BeaconReference
import com.example.projecto_suarez.services.BeaconScanner
import com.example.projecto_suarez.util.BeaconLibrary.BleScanCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class BeaconViewModel @Inject constructor(
    private val beaconScanner: BeaconScanner
): ViewModel(){
    val _state = mutableStateOf(MapState());
    val state : State<MapState> = _state;

    init {
        beaconScanner.initBluetooth()
        beaconScanner.bluetoothScanStart(beaconScanner.bleScanCallback)
    }

    companion object {
        val TAG = "BeaconViewModel"
    }
}