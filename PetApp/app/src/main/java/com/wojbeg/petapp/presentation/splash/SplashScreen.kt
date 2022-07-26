package com.wojbeg.petapp.presentation.splash

import androidx.compose.animation.core.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wojbeg.petapp.presentation.ui.theme.niceGreen
import com.wojbeg.petapp.presentation.util.Screen
import kotlinx.coroutines.delay
import com.wojbeg.petapp.R
import com.wojbeg.petapp.common.Constants
import com.wojbeg.petapp.common.Fonts

@Composable
fun SplashScreen(
    navController: NavController
) {

    val androidScale = remember {
        Animatable(0f)
    }

    val goScale = remember {
        Animatable(0f)
    }

    val titleAlphaAnim = remember {
        Animatable(0f)
    }

    val offsetAnim = remember {
        Animatable(-1f)
    }

    val poweredByAlpha = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        poweredByAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    LaunchedEffect(key1 = true) {

        titleAlphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )

        offsetAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 500,
                easing = FastOutSlowInEasing
            )
        )

        androidScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )

        goScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
    }

    LaunchedEffect(key1 = true) {
        delay(Constants.SPLASH_SCREEN_DURATION)
        navController.popBackStack()
        navController.navigate(Screen.PetListScreen.route)
    }

    Splash(titleAlphaAnim.value, poweredByAlpha.value, offsetAnim.value, 150, androidScale.value, goScale.value)
}

@Composable
fun Splash(
    titleAlpha: Float,
    poweredByAlpha: Float,
    poweredByOffset: Float,
    poweredByMax: Int,
    androidIconScale: Float,
    goIconScale: Float
) {
    Box(
        modifier = Modifier
            .background(niceGreen)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.splash_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
                    .alpha(titleAlpha),
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.josefinFamily,
                textAlign = TextAlign.Center
            )


            Text(
                text = stringResource(id = R.string.powered_by),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .offset(x = (poweredByOffset * poweredByMax).toInt().dp)
                    .alpha(poweredByAlpha),
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontFamily = Fonts.josefinFamily,
                textAlign = TextAlign.Center,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_android_logo),
                    contentDescription = stringResource(id = R.string.icon_android),
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .size(120.dp)
                        .scale(androidIconScale),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(R.drawable.ic_go),
                    contentDescription = stringResource(id = R.string.icon_golang),
                    modifier = Modifier
                        .size(150.dp)
                        .padding(end = 16.dp)
                        .scale(goIconScale),
                    tint = Color.Unspecified
                )
            }
        }
    }
}