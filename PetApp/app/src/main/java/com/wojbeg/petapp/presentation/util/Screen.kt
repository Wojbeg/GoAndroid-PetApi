package com.wojbeg.petapp.presentation.util

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object PetListScreen: Screen("pet_list_screen")
    object PetDetailsScreen: Screen("pet_details_screen")
}