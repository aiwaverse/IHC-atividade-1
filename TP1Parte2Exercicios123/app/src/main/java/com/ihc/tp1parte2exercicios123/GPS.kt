package com.ihc.tp1parte2exercicios123

import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationResult

class GPS(
    val fusedLocationProviderClient: FusedLocationProviderClient,
    val checkPermissions: () -> Boolean,
    val callback: (LocationResult) -> Unit,
) {
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission(),
//    ) {
//    }
//    init {
//        if (!checkPermissions()) {
//
//        }
//    }
}
