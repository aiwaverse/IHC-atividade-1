package com.ihc.tp1parte2exercicios123

import android.location.Location
import android.location.LocationListener

class GPS(val locationChangedCallback: (Location) -> Unit) : LocationListener {
    override fun onLocationChanged(location: Location) {
        locationChangedCallback(location)
    }
}
