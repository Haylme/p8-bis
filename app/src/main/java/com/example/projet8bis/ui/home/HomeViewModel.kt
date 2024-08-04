package com.example.projet8bis.ui.home

import androidx.lifecycle.ViewModel
import com.example.projet8bis.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}