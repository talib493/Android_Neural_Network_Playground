package com.example.neuralnetwork.navigation

import android.service.autofill.Dataset
import com.example.neuralnetwork.dataClasses.FirebaseModelData
import kotlinx.serialization.Serializable
import java.io.Serial

sealed interface Routes {
    @Serializable
    data object Register:Routes
    @Serializable
    data object Main: Routes
    @Serializable
    data object Create: Routes
    @Serializable
    data class Train(
        val networkName: String = "",
        val dataset: String = "",
        val learningRate: Float = 0f,
        val epochs : String = "",
        val id: String=""
    ): Routes
    @Serializable
    data object MyNetworks: Routes
    @Serializable
    data class Try(
        val id : String = ""
    ): Routes
    @Serializable
    data class Inspect(
        val id : String = ""
    ): Routes
    @Serializable
    data object Settings : Routes

}


