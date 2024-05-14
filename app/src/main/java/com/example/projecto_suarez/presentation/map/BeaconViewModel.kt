package com.example.projecto_suarez.presentation.map

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.altbeacon.beacon.*
import com.example.projecto_suarez.services.BeaconReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class BeaconViewModel  @Inject constructor(
    private val beaconReference: BeaconReference
): ViewModel(){
    val _state = mutableStateOf(MapState());
    val state : State<MapState> = _state;

    val monitoringObserver = Observer<Int> { state ->
        var dialogTitle = "Beacons detected"
        var dialogMessage = "didEnterRegionEvent has fired"
        var stateString = "inside"
        if (state == MonitorNotifier.OUTSIDE) {
            dialogTitle = "No beacons detected"
            dialogMessage = "didExitRegionEvent has fired"
            stateString == "outside"
            //beaconCountTextView.text = "Outside of the beacon region -- no beacons detected"
            //beaconListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOf("--"))

            Log.d(TAG, dialogTitle)

        }
        else {
            //beaconCountTextView.text = "Inside the beacon region."
            Log.d(TAG, "Inside the beacon region.")

        }
        Log.d(TAG, "monitoring state changed to : $stateString")
    }

    val rangingObserver = Observer<Collection<Beacon>> { beacons ->
        Log.d(TAG, "Ranged: ${beacons.count()} beacons")
        if (beaconReference.getBeaconManager().rangedRegions.isNotEmpty()) {
            Log.d(TAG, beacons.toString())
            val _beacons = beacons
                .sortedBy { it.distance }
                .map { "${it.id1}\nid2: ${it.id2} id3:  rssi: ${it.rssi}\nest. distance: ${it.distance} m" }.toTypedArray()
            _state.value = state.value.copy(beacons = _beacons);
        }
    }


    init {
        // Set up a Live Data observer for beacon data
        val regionViewModel = beaconReference.getBeaconManager().getRegionViewModel(beaconReference.region)
        // observer will be called each time the monitored regionState changes (inside vs. outside region)
        regionViewModel.regionState.observeForever(monitoringObserver);
        // observer will be called each time a new list of beacons is ranged (typically ~1 second in the foreground)
        regionViewModel.rangedBeacons.observeForever( rangingObserver)
    }

    override fun onCleared() {
        val regionViewModel = beaconReference.getBeaconManager().getRegionViewModel(beaconReference.region)
        regionViewModel.regionState.removeObserver(monitoringObserver);
        regionViewModel.rangedBeacons.removeObserver(rangingObserver);
        super.onCleared()
    }
    companion object {
        val TAG = "BeaconViewModel"
    }
}