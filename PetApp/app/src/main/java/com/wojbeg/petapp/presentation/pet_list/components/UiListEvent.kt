package com.wojbeg.petapp.presentation.pet_list.components

sealed class UiListEvent{
    data class ShowErrorSnackbar(val message: String): UiListEvent()
    object DeletedSuccess: UiListEvent()
}
