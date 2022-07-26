package com.wojbeg.petapp.presentation.pet_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.wojbeg.petapp.R
import com.wojbeg.petapp.presentation.pet_detail.components.PetDetailsEvent
import com.wojbeg.petapp.presentation.pet_detail.components.UiEvent
import com.wojbeg.petapp.presentation.pet_detail.ui_components.TopSection
import com.wojbeg.petapp.presentation.ui.theme.niceGreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PetDetailsScreen(
    petJson: String?,
    navController: NavController,
    topPadding: Dp = 20.dp,
    imageSize: Dp = 200.dp,
    viewModel: PetDetailsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        petJson?.let {
            viewModel.onEvent(PetDetailsEvent.SetPet(it))
        }
    }

    val scrollState = rememberScrollState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.SavedSuccess -> {
                    navController.popBackStack()
                }
                is UiEvent.ShowErrorSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    val state = viewModel.state.value

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.pet.photo.trim())
            .size(Size.ORIGINAL)
            .crossfade(enable = true)
            .crossfade(500)
            .placeholder(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_placeholder)
            .build(),
    )

    val inputStyle = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = niceGreen,
        focusedLabelColor = MaterialTheme.colors.onSurface
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!state.isLoading){
                        viewModel.onEvent(PetDetailsEvent.SavePet)
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
            ) {
                Icon(imageVector = Icons.Filled.Send, contentDescription = stringResource(id = R.string.save_pet))
            }
        },
        scaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                Box {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = topPadding + imageSize / 2f,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 48.dp
                            )
                            .shadow(10.dp, RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 80.dp)
                            .padding(bottom = 100.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(32.dp))

                        OutlinedTextField(
                            value = state.pet.name,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterName(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_name))
                            },
                            colors = inputStyle,
                            isError = state.nameError,
                            trailingIcon = {
                                if (state.nameError) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = stringResource(id = R.string.error_empty_pet_name),
                                        tint = Color.Red
                                    )
                                }
                            },

                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = state.age,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterAge(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_age))
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = inputStyle,
                            isError = state.ageError,
                            trailingIcon = {
                                if (state.ageError) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = stringResource(id = R.string.error_empty_pet_age),
                                        tint = Color.Red
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = state.pet.species,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterSpecies(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_species))
                            },
                            colors = inputStyle
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = state.pet.food,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterFood(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_food))
                            },
                            colors = inputStyle
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = state.pet.description,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterDesc(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_description))
                            },
                            colors = inputStyle
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = state.photoUrl,
                            onValueChange = {
                                viewModel.onEvent(PetDetailsEvent.EnterPhoto(it))
                            },
                            label = {
                                Text(text = stringResource(id = R.string.enter_photo))
                            },
                            colors = inputStyle
                        )

                    }

                    Box(
                        modifier = Modifier
                            .padding(32.dp)
                            .size(imageSize)
                            .shadow(5.dp, RoundedCornerShape(15.dp))
                            .clip(RoundedCornerShape(15.dp))
                            .align(Alignment.TopCenter),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        if (painter.state is AsyncImagePainter.State.Loading){

                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize(0.5f)
                                    .align(Alignment.Center),
                                color = MaterialTheme.colors.onSurface,
                            )
                        } else {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(15.dp))
                                    .align(Alignment.Center),
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }

                }
            }

            TopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(0.25f)
                        .align(Alignment.Center),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}