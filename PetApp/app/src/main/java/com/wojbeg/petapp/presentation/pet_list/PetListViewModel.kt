package com.wojbeg.petapp.presentation.pet_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.wojbeg.petapp.common.Resource
import com.wojbeg.petapp.domain.model.Pet
import com.wojbeg.petapp.domain.use_case.ListUseCases
import com.wojbeg.petapp.presentation.pet_list.components.PetListEvent
import com.wojbeg.petapp.presentation.pet_list.components.PetsState
import com.wojbeg.petapp.presentation.pet_list.components.UiListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PetListViewModel @Inject constructor(
    val listUseCases: ListUseCases
): ViewModel() {

    private val _state = mutableStateOf(PetsState())
    val state: State<PetsState> = _state

    private val _eventFlow = MutableSharedFlow<UiListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val gson = GsonBuilder().disableHtmlEscaping().create()

    fun getPetJson(pet: Pet): String {
        return gson.toJson(pet, Pet::class.java)
    }

    fun onEvent(event: PetListEvent){

        when(event) {

            is PetListEvent.GetPets -> {
                getPets()
            }

            is PetListEvent.SetDialog -> {

                _state.value = state.value.copy(
                    isDialogOpen = event.value,
                    idPreparedToDelete = event.idToDelete
                )
            }

            is PetListEvent.DeletePet -> {
                deletePet()
            }
        }
    }

    private fun getPets() {
        listUseCases.getPets().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    result.data?.forEach {
                        it.toString()
                    }
                    _state.value = PetsState(pets = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = PetsState(isLoading = true, error = "")
                }
                is Resource.Error -> {
                    _state.value = PetsState(error = result.message ?:
                    "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deletePet() {

        listUseCases.deletePet(_state.value.idPreparedToDelete).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiListEvent.DeletedSuccess
                    )
                    getPets()
                }
                is Resource.Loading -> {
                    _state.value = state.value.copy(
                        isLoading = true,
                        error = ""
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiListEvent.ShowErrorSnackbar(
                            result.message ?: "An unexpected error occurred"
                        )
                    )
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}