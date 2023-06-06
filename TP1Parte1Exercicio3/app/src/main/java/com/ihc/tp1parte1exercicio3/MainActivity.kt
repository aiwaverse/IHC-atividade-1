package com.ihc.tp1parte1exercicio3

import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ihc.tp1parte1exercicio3.ui.theme.TP1Parte1Exercicio3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP1Parte1Exercicio3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Atividade3App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Atividade3App() {
    val navController = rememberNavController()

    var coordinates by remember { mutableStateOf(Triple(0F, 0F, 0F)) }
    val sensorManager = LocalContext.current.getSystemService(SENSOR_SERVICE) as SensorManager
    Accelerometer(sensorManager) {
        coordinates = Triple(it[0], it[1], it[2])
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.atividade_3),
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
            NavHost(navController = navController, startDestination = "accelerometer") {
                composable("accelerometer") {
                    // Z coordinate has gravity information so we ignore it by setting the limit to infinity
                    AccelerometerInformation(coordinates, Triple(10F, 10F, Float.POSITIVE_INFINITY)) {
                        navController.navigate("display")
                    }
                }
                composable("display") {
                    MessageDisplay(message = "Feliz mês do Orgulho LGBTQIA+!")
                }
            }
        }
    }
}

@Composable
fun MessageDisplay(message: String) {
    Text(
        text = message,
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccelerometerInformation(
    coordinates: Triple<Float, Float, Float>,
    limit: Triple<Float, Float, Float>,
    onLimit: () -> Unit,
) {
    if (coordinates.first > limit.first || coordinates.second > limit.second || coordinates.third > limit.third) {
        onLimit()
    }
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            readOnly = true,
            value = "%.2f".format(coordinates.first),
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
            label = { Text(stringResource(R.string.x)) },
        )
        TextField(
            readOnly = true,
            value = "%.2f".format(coordinates.second),
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
            label = { Text(stringResource(R.string.y)) },
        )
        TextField(
            readOnly = true,
            value = "%.2f".format(coordinates.third),
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
            label = { Text(stringResource(R.string.z)) },
        )
    }
}
