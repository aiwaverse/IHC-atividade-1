package com.ihc.tp1parte2exercicios123

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_NORMAL

class GenericSensor(private val sensorManager: SensorManager, sensor: Sensor?, val onChange: (FloatArray) -> Unit) : SensorEventListener {

    init {
        this.sensorManager.registerListener(this, sensor, SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val sensor = event?.sensor
        if (sensor != null) {
            onChange(event.values)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
