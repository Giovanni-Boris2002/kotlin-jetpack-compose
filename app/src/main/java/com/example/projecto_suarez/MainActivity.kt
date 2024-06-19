package com.example.projecto_suarez

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.projecto_suarez.presentation.navgraph.NavGraph
import com.example.projecto_suarez.ui.theme.ProjectosuarezTheme
import dagger.hilt.android.AndroidEntryPoint
import android.Manifest
import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import com.example.projecto_suarez.util.Constants
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
            setOnExitAnimationListener { screen ->
                val zoomX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                zoomX.interpolator = OvershootInterpolator()
                zoomX.duration = 500L
                zoomX.doOnEnd { screen.remove() }

                val zoomY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                zoomY.interpolator = OvershootInterpolator()
                zoomY.duration = 500L
                zoomY.doOnEnd { screen.remove() }

                zoomX.start()
                zoomY.start()
            }
        }

        setContent {
            var requestPermissionsGranted by remember { mutableStateOf(areAllPermissionsGranted(Constants.PERMISSIONS)) }
            var shouldShowPermissionRationale by remember {
                mutableStateOf(
                    shouldShowRequestPermissionAllRationale(Constants.PERMISSIONS)
                )
            }

            var shouldDirectUserToApplicationSettings by remember {
                mutableStateOf(false)
            }

            var currentPermissionsStatus by remember {
                mutableStateOf(decideCurrentPermissionStatus(requestPermissionsGranted, shouldShowPermissionRationale))
            }

            val androidPermissions = Constants.PERMISSIONS

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissions ->
                    requestPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                        acc && isPermissionGranted
                    }

                    if (!requestPermissionsGranted) {
                        shouldShowPermissionRationale =
                            shouldShowRequestPermissionAllRationale(Constants.PERMISSIONS)
                    }
                    shouldDirectUserToApplicationSettings = !shouldShowPermissionRationale && !requestPermissionsGranted
                    currentPermissionsStatus = decideCurrentPermissionStatus(requestPermissionsGranted, shouldShowPermissionRationale)
                })

            val lifecycleOwner = LocalLifecycleOwner.current
            DisposableEffect(key1 = lifecycleOwner, effect = {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_START &&
                        !requestPermissionsGranted &&
                        !shouldShowPermissionRationale) {
                        requestPermissionLauncher.launch(androidPermissions)
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            })

            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }

            ProjectosuarezTheme {
                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = !isSystemInDarkMode
                    )
                }
                Scaffold(snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }) { contentPadding ->
                    // A surface container using the 'background' color from the theme
                    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                        NavGraph(startDestination = viewModel.startDestination)
                    }
                    Log.d("Padding", contentPadding.toString())
                    if (shouldShowPermissionRationale) {
                        LaunchedEffect(Unit) {
                            scope.launch {
                                val userAction = snackbarHostState.showSnackbar(
                                    message ="Please authorize permissions to work with the app",
                                    actionLabel = "Approve",
                                    duration = SnackbarDuration.Indefinite,
                                    withDismissAction = true
                                )
                                when (userAction) {
                                    SnackbarResult.ActionPerformed -> {
                                        shouldShowPermissionRationale = false
                                        requestPermissionLauncher.launch(androidPermissions)
                                    }
                                    SnackbarResult.Dismissed -> {
                                        shouldShowPermissionRationale = false
                                    }

                                }
                            }
                        }
                    }
                    if (shouldDirectUserToApplicationSettings) {
                        openApplicationSettings()
                    }
                }


            }
        }
    }

    companion object {
        val TAG = "MainActivity"
    }
    private fun shouldShowRequestPermissionAllRationale(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true
            }
        }
        return false
    }
    private fun areAllPermissionsGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)!= PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun openApplicationSettings() {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", packageName, null)).also {
            startActivity(it)
        }
    }

    private fun decideCurrentPermissionStatus(locationPermissionsGranted: Boolean,
                                              shouldShowPermissionRationale: Boolean): String {
        return if (locationPermissionsGranted) "Granted"
        else if (shouldShowPermissionRationale) "Rejected"
        else "Denied"
    }


}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjectosuarezTheme {
        Greeting("Android")
    }
}