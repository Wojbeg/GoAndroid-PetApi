package com.wojbeg.petapp.presentation.pet_list.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wojbeg.petapp.R
import com.wojbeg.petapp.common.Fonts
import com.wojbeg.petapp.presentation.ui.theme.niceGreen

@Composable
fun RoundedTopBar(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 15.dp,
    barColor: Color = niceGreen,
    text: String = stringResource(id = R.string.pets_bar),
    textColor: Color = MaterialTheme.colors.onSurface,
    textSize: TextUnit = 25.sp
) {

    Box(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(RoundedCornerShape(bottomStart = cornerRadius+5.dp, bottomEnd = cornerRadius+5.dp))
        .background(barColor),
        contentAlignment = Alignment.TopCenter,
    ) {

        Text(
            modifier = modifier.fillMaxWidth().padding(16.dp),
            text = text,
            color = textColor,
            fontSize = textSize,
            fontFamily = Fonts.josefinFamily,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }

}