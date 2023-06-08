package com.ihc.tp1parte1exercicio3

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL

class Accelerometer private constructor(
    val sensorManager: SensorManager,
    private val accelerometer: Sensor,
    val callback: (FloatArray) -> Unit,
) : SensorEventListener {

    constructor(sensorManager: SensorManager, callback: (FloatArray) -> Unit) : this(
        sensorManager,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        callback,
    ) {
        sensorManager.registerListener(this, accelerometer, SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        if (sensor != null) {
            callback(event.values)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
