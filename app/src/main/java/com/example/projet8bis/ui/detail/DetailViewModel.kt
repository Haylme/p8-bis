package com.example.projet8bis.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet8bis.data.DataRepository
import com.example.projet8bis.model.Content
import com.example.projet8bis.model.SimpleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataRepository: DataRepository,

    ) : ViewModel() {


    private val _detailError = MutableStateFlow<String?>(null)
    val detailError: StateFlow<String?> = _detailError

    private val _detailAdd = MutableStateFlow(SimpleResponse.initial<Content?>())
    val detailAdd: StateFlow<SimpleResponse<Content?>> = _detailAdd.asStateFlow()


    private val _updateUserFav = MutableStateFlow(SimpleResponse.initial<Boolean>())
    val updateUserFav: StateFlow<SimpleResponse<Boolean>> = _updateUserFav.asStateFlow()

    private val _translate = MutableStateFlow(SimpleResponse.initial<Double>())
    val translate: StateFlow<SimpleResponse<Double>> = _translate.asStateFlow()

    fun resetToast() {
        _detailError.value = null

    }

    fun resetError() {

        _detailAdd.value = SimpleResponse.initial()
    }








    fun updateData(id: Long, bool: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.updateFav(id, bool)


            _updateUserFav.value = SimpleResponse.success(bool)
        }
    }


    fun deleteCandidate(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataRepository.suppressUser(id)
                _detailAdd.value = SimpleResponse.success(null)
            } catch (e: Exception) {
                _detailAdd.value = SimpleResponse.failure(Exception("Unknown error"))
                _detailError.value = "Unknown error"
            }
        }
    }

}
