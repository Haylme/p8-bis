package com.example.projet8bis.ui.detail

import androidx.lifecycle.ViewModel
import com.example.projet8bis.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,

    ) : ViewModel() {
}