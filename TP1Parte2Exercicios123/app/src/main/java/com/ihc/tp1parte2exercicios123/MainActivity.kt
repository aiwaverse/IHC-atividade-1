package com.ihc.tp1parte2exercicios123

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.ihc.tp1parte2exercicios123.ui.theme.TP1Parte2Exercicios123Theme

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            TP1Parte2Exercicios123Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
//                    if (!hasLocationPermission(this)) {
//                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//                    }
                    Atividade4App(fusedLocationClient)
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Atividade4App(
    fusedLocationClient: FusedLocationProviderClient? = null,
) {
    var ambientLightLevel by remember { mutableStateOf(0F) }
    var pressure by remember { mutableStateOf(0F) }
    var latitude: Double? by remember { mutableStateOf(null) }
    var longitude: Double? by remember { mutableStateOf(null) }
    var hasPermission: Boolean? by remember { mutableStateOf(null) }

    // Launcher to ask for location permissions
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        hasPermission = it
    }

    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    GenericSensor(sensorManager, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)) {
        ambientLightLevel = it[0]
    }
    GenericSensor(sensorManager, sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)) {
        pressure = it[0]
    }
    if (fusedLocationClient != null && hasPermission == true) {
        // Using the fusedLocationClient we can request for GPS updates every second
        val locationRequest = LocationRequest.Builder(1000).build()
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                longitude = locationResult.lastLocation?.longitude
                latitude = locationResult.lastLocation?.latitude
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    // The launcher.launch() is a side effect so we use LaunchedEffect
    LaunchedEffect(launcher) {
        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.atividade_4),
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                modifier = Modifier.padding(bottom = 8.dp),
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background,
        ) {
            val infoList = mutableListOf(
                listOf(Pair("Light", "$ambientLightLevel lux")),
                listOf(Pair("Pressure", "$pressure hPa")),
            )
            // It will not contain GPS info if the permission was never given
            if (longitude != null && latitude != null) {
                infoList.add(
                    listOf(
                        Pair("Longitude", longitude.toString()),
                        Pair("Latitude", latitude.toString()),
                    ),
                )
            }
            SensorInfoDisplay(infoList)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorInfoDisplay(information: List<List<Pair<String, String>>>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        information.forEach {
            Row {
                it.forEach { (label, value) ->
                    TextField(
                        readOnly = true,
                        value = value,
                        onValueChange = {},
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1.0F),
                        shape = RoundedCornerShape(15.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        ),
                        label = { Text(label) },
                    )
                }
            }
        }
    }
}
