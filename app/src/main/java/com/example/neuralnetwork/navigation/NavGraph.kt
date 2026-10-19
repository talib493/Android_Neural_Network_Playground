package com.example.neuralnetwork.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.neuralnetwork.screens.CreateScreen
import com.example.neuralnetwork.screens.MainScreen
import com.example.neuralnetwork.screens.MyNetworks
import com.example.neuralnetwork.screens.RegisterScreen
import com.example.neuralnetwork.screens.SettingsScreen
import com.example.neuralnetwork.screens.TrainScreen
import com.example.neuralnetwork.screens.TryScreen
import com.example.neuralnetwork.screens.inspectScreen
import com.google.firebase.auth.FirebaseUser

@Composable
fun NavGraph(currentUser: FirebaseUser?){
    val navController = rememberNavController()
    val startDestination =
        if (currentUser != null) {
            Routes.Main
        } else {
            Routes.Register
        }


    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable<Routes.Register>{
            RegisterScreen(
                onNavigateToMain ={navController.navigate(Routes.Main){
                    popUpTo<Routes.Register>{
                        inclusive=true
                    }
                } },
            )
        }
        composable<Routes.Main>{
            MainScreen(
                onNavigationToCreate = {
                    navController.navigate(Routes.Create)
                },
                onNavigateToMyNetworks ={
                    navController.navigate(Routes.MyNetworks)
                },
                onNavigateToTry ={t->
                    navController.navigate(t)
                },
                onNavigateToInspect = {i->
                    navController.navigate(i)
                },
                onNavigationToSettings = {
                    navController.navigate(Routes.Settings)
                }
            )
        }
        composable<Routes.Create>{
            CreateScreen(
                onNavigationToTrain ={train -> navController.navigate(train)}
            )
        }
        composable<Routes.Train>{backstackentry->
            val args = backstackentry.toRoute<Routes.Train>()
            TrainScreen(
                args = args
            )
        }
        composable<Routes.MyNetworks>{
            MyNetworks(
                onNavigateToTry = {t -> navController.navigate(t)},
                onNavigateToTrain = {train -> navController.navigate(train)},
                onNavigationToInspect = {inspect-> navController.navigate(inspect)}
            )

        }
        composable<Routes.Try>{backStackEntry->
            val args = backStackEntry.toRoute<Routes.Try>()
            TryScreen(
                args = args
            )
        }
        composable<Routes.Inspect>{backstackEntry->
            val args = backstackEntry.toRoute<Routes.Inspect>()
            inspectScreen(
                args = args
            )
        }
        composable<Routes.Settings>{
            SettingsScreen(
                onNavigateToRegister = { navController.navigate(Routes.Register){
                    popUpTo(0){
                        inclusive = true
                    }
                } }
            )
        }
    }
}