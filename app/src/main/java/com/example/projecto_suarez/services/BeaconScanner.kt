package com.example.projecto_suarez.services

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.projecto_suarez.util.BeaconLibrary.Beacon
import com.example.projecto_suarez.util.BeaconLibrary.BeaconParser
import com.example.projecto_suarez.util.BeaconLibrary.BleScanCallback
import com.example.projecto_suarez.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class BeaconScanner(private val context: Context) {
    private val TAG: String = "BeaconScanner"

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var btScanner: BluetoothLeScanner? = null;
    private val beacons = HashMap<String, Beacon>();
    private val _resultBeacons = MutableStateFlow("No beacons Detected")
    val resultBeacons: StateFlow<String> = _resultBeacons


    fun initBluetooth() {

        bluetoothManager = getSystemService(context,BluetoothManager::class.java)!!
        bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter != null) {
            btScanner = bluetoothAdapter.bluetoothLeScanner
        } else {
            Log.d(TAG, "BluetoothAdapter is null")
        }
    }

    fun bluetoothScanStart(bleScanCallback: BleScanCallback) {
        Log.d(TAG, "btScan ...1")
        if (btScanner != null) {

            Log.d(TAG, "btScan ...2")

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            if(!checkPermissions(context, Constants.PERMISSIONS)) return;
            btScanner?.startScan(bleScanCallback)

        } else {
            Log.d(TAG, "btScanner is null")
        }

    }
    private fun checkPermissions(context: Context, permissions: Array<String>): Boolean{
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
    @SuppressLint("MissingPermission")
    private fun bluetoothScanStop(bleScanCallback: BleScanCallback) {
        Log.d(TAG, "btScan ...1")
        if (btScanner != null) {
            Log.d(TAG, "btScan ...2")
            btScanner!!.stopScan(bleScanCallback)

        } else {
            Log.d(TAG, "btScanner is null")
        }

    }

    @SuppressLint("MissingPermission")
    val onScanResultAction: (ScanResult?) -> Unit = { result ->
        Log.d(TAG, "onScanResultAction ")

        val scanRecord = result?.scanRecord

        var rssi = result?.rssi
        if (scanRecord != null) {
            scanRecord?.bytes?.let {
                val parserBeacon = BeaconParser.parseIBeacon(it, rssi)
                if (!beacons.containsKey(parserBeacon.uuid)){
                    parserBeacon.uuid?.let { it1 -> beacons.put(it1, parserBeacon) }
                }
                val beaconSave = beacons.get(parserBeacon.uuid)
                if (beaconSave != null) {
                    beaconSave.rssi = parserBeacon.rssi
                };
                val distance = parserBeacon.txPower?.let { it1 -> parserBeacon.rssi?.let { it2 -> beaconSave?.calculateDistance(txPower = it1, rssi = it2) } }
                _resultBeacons.value =  beaconSave.toString() + "distance "+ distance;
                Log.d(TAG, beaconSave.toString() + "distance "+ distance);

            }
        }

    }

    val onBatchScanResultAction: (MutableList<ScanResult>?) -> Unit = {
        if (it != null) {
            Log.d(TAG, "BatchScanResult " + it.toString())
        }
    }

    val onScanFailedAction: (Int) -> Unit = {
        Log.d(TAG, "ScanFailed " + it.toString())
    }
    val bleScanCallback = BleScanCallback(
        onScanResultAction,
        onBatchScanResultAction,
        onScanFailedAction
    )
    fun changeDetection(updateUI: (String)->Unit, result:String){
        updateUI(result)
    }

}