package com.wojbeg.petapp.presentation.pet_detail.components

sealed class UiEvent{
    data class ShowErrorSnackbar(val message: String): UiEvent()
    object SavedSuccess: UiEvent()
}
