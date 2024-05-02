package com.example.projecto_suarez.presentation.map

import android.R

import android.util.Log

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
        if (beaconReference.getBeaconManager().rangedRegions.size > 0) {
            Log.d(TAG, "Becauns found")
            Log.d(TAG, beacons.toString())
        }
    }
    init {
        // Set up a Live Data observer for beacon data
        val regionViewModel = beaconReference.getBeaconManager().getRegionViewModel(beaconReference.region)
        // observer will be called each time the monitored regionState changes (inside vs. outside region)
        regionViewModel.regionState.observeForever( monitoringObserver)
        // observer will be called each time a new list of beacons is ranged (typically ~1 second in the foreground)
        regionViewModel.rangedBeacons.observeForever( rangingObserver)
    }
    companion object {
        val TAG = "BeaconViewModel"
    }

}