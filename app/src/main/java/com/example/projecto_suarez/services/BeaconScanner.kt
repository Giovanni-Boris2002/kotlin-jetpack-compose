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
import androidx.core.content.ContextCompat.getSystemService
import com.example.projecto_suarez.util.BeaconLibrary.Beacon
import com.example.projecto_suarez.util.BeaconLibrary.BeaconParser
import com.example.projecto_suarez.util.BeaconLibrary.BleScanCallback


class BeaconScanner(private val context: Context) {
    private val TAG: String = "BeaconScanner"

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var btScanner: BluetoothLeScanner
    private val beacons = HashMap<String, Beacon>()

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

            }
            btScanner.startScan(bleScanCallback)

        } else {
            Log.d(TAG, "btScanner is null")
        }

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

}