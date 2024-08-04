package com.example.projet8bis.ui.edit

import androidx.lifecycle.ViewModel
import com.example.projet8bis.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EditViewModel @Inject constructor(
    private val dataRepository: DataRepository,

    ) : ViewModel() {
}