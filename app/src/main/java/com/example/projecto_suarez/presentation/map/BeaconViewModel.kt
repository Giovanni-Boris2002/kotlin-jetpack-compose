package com.example.projecto_suarez.presentation.map

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import com.example.projecto_suarez.services.BeaconScanner
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