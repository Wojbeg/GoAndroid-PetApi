package com.wojbeg.petapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.wojbeg.petapp.presentation.pet_detail.PetDetailsScreen
import com.wojbeg.petapp.presentation.pet_list.PetListScreen
import com.wojbeg.petapp.presentation.splash.SplashScreen
import com.wojbeg.petapp.presentation.ui.theme.PetAppTheme
import com.wojbeg.petapp.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(
                            route = Screen.SplashScreen.route
                        ) {
                            SplashScreen(navController = navController)
                        }

                        composable(
                            route = Screen.PetListScreen.route
                        ) {
                            PetListScreen(navController = navController)
                        }

                        composable(
                            route = Screen.PetDetailsScreen.route + "?pet={pet}",
                            arguments = listOf(
                                navArgument("pet") {
                                    nullable = true
                                    defaultValue = null
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val pet = remember {
                                it.arguments?.getString("pet")
                            }

                            PetDetailsScreen(
                                petJson = pet,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
