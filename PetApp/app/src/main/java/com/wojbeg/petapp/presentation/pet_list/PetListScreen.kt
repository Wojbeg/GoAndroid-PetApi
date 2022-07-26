package com.wojbeg.petapp.presentation.pet_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.wojbeg.petapp.R
import com.wojbeg.petapp.common.Fonts
import com.wojbeg.petapp.presentation.pet_list.components.PetListEvent
import com.wojbeg.petapp.presentation.pet_list.components.UiListEvent
import com.wojbeg.petapp.presentation.pet_list.ui_components.PetListItem
import com.wojbeg.petapp.presentation.pet_list.ui_components.RoundedTopBar
import com.wojbeg.petapp.presentation.ui.theme.backgroundGreenDark
import com.wojbeg.petapp.presentation.ui.theme.niceGreen
import com.wojbeg.petapp.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PetListScreen(
    navController: NavController,
    viewModel: PetListViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value

    val stringDeleted = stringResource(id = R.string.deleted_success)

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiListEvent.DeletedSuccess -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = stringDeleted
                    )
                }
                is UiListEvent.ShowErrorSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true){
        viewModel.onEvent(PetListEvent.GetPets)
    }

    Scaffold( modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(
                        Screen.PetDetailsScreen.route
                    )
                },
                backgroundColor = niceGreen,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_btn))
            }
        },
        scaffoldState = scaffoldState) {
        
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = false),
            onRefresh = {
                viewModel.onEvent(PetListEvent.GetPets)
            },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    scale = true,
                    backgroundColor = backgroundGreenDark
                )
            }
        ) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                ) {

                    RoundedTopBar(modifier = Modifier.fillMaxWidth())

                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            items(state.pets) { pet ->
                                PetListItem(
                                    modifier = Modifier.height(150.dp),
                                    pet = pet,
                                    navController = navController,
                                    viewModel = viewModel,
                                    onDelete = {
                                        viewModel.onEvent(PetListEvent.SetDialog(true, pet.id))
                                    }
                                )
                            }
                        }
                }

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize(0.25f)
                            .align(Alignment.Center),
                        color = MaterialTheme.colors.onSurface
                    )
                } else {
                    if (state.pets.isEmpty() && state.error.isBlank()) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize(0.5f)
                                .align(Alignment.Center)
                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_no_data),
                                contentDescription = stringResource(id = R.string.no_data),
                            )

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp, vertical = 16.dp),
                                text = stringResource(id = R.string.no_data),
                                color = MaterialTheme.colors.onSurface,
                                fontSize = 16.sp,
                                fontFamily = Fonts.josefinFamily,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    } else if(state.error.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize(0.5f)
                                .align(Alignment.Center)
                                .padding(horizontal = 32.dp, vertical = 16.dp),
                            text = state.error,
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontFamily = Fonts.josefinFamily,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if(state.isDialogOpen){
                    AlertDialog(
                        onDismissRequest = { viewModel.onEvent(PetListEvent.SetDialog(false)) },
                        title = {
                            Text(
                                text = stringResource(id = R.string.delete_pet),
                                color = MaterialTheme.colors.onSurface
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(id = R.string.delete_info),
                                color = MaterialTheme.colors.onSurface
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.onEvent(PetListEvent.DeletePet)
                                viewModel.onEvent(PetListEvent.SetDialog(false))

                            }) {
                                Text(
                                    text = stringResource(id = R.string.delete),
                                    color = MaterialTheme.colors.onSurface
                                )
                            }

                        },
                        dismissButton = {
                            TextButton(onClick = {
                                viewModel.onEvent(PetListEvent.SetDialog(false))
                            }) {
                                Text(
                                    text = stringResource(id = R.string.cancel),
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        },
                        backgroundColor = MaterialTheme.colors.surface,
                        contentColor = MaterialTheme.colors.background
                    )
                }

            }
        }
    }
}