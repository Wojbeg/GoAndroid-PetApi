package com.wojbeg.petapp.common

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.wojbeg.petapp.R

object Fonts {

    val josefinFamily = FontFamily(
        Font(R.font.josefin_sans_bold, FontWeight.Bold),
        Font(R.font.josefin_sans_bolditalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.josefin_sans_extralight, FontWeight.ExtraLight),
        Font(R.font.josefinsans_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(R.font.josefinsans_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.josefinsans_lightitalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.josefinsans_medium, FontWeight.Medium),
        Font(R.font.josefinsans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.josefinsans_regular, FontWeight.Normal),
        Font(R.font.josefinsans_semibold, FontWeight.SemiBold),
        Font(R.font.josefinsans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    )
}