package com.wojbeg.petapp.presentation.pet_list.ui_components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.wojbeg.petapp.R
import com.wojbeg.petapp.common.Fonts
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.presentation.pet_list.PetListViewModel
import com.wojbeg.petapp.presentation.util.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PetListItem(
    pet: Pet,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PetListViewModel,
    onDelete: () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.surface)
            .combinedClickable (
                onClick = {
                    val petJson = viewModel.getPetJson(pet)
                    navController.navigate(
                        Screen.PetDetailsScreen.route + "?pet=$petJson"
                    )
                },
                onLongClick = {
                    onDelete()
                }
            ),
        horizontalArrangement = Arrangement.Center
    ) {

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pet.photo.trim())
                .size(Size.ORIGINAL)
                .crossfade(enable = true)
                .crossfade(500)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .build(),
        )

        Box(modifier = Modifier.weight(1f, true), contentAlignment = Alignment.Center) {

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                )
            } else {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

        Box(modifier = Modifier
            .weight(2f, true)
            .fillMaxHeight()
            .padding(8.dp),
        ) {
            Text(
                text = pet.name.trim(),
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .offset(y = (-20).dp),
                color = MaterialTheme.colors.onSurface,
                fontSize = 25.sp,
                fontFamily = Fonts.josefinFamily,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier
                        .weight(2f, true)
                        .padding(end = 8.dp),
                    text = stringResource(id = R.string.species, pet.species.trim()),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 15.sp,
                    fontFamily = Fonts.josefinFamily,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(start = 8.dp),
                    text = stringResource(id = R.string.age, pet.age),
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 15.sp,
                    fontFamily = Fonts.josefinFamily,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}