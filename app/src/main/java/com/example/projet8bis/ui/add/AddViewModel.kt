package com.example.projet8bis.ui.add

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
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {


    private val EMAIL_ADDRESS_PATTERN: Pattern
        get() = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +

                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )

    fun checkMail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }


    fun checkField(
        nom: String,
        prenom: String,
        phone: String,
        email: String,
        date: String,
        wage: Double,
        note: String
    ): Boolean {

        return nom.isNotEmpty() && prenom.isNotEmpty() && phone.isNotEmpty() &&
                email.isNotEmpty() && date.isNotEmpty() && wage > 0 && note.isNotEmpty()
    }


    private val _userError = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _userError

    private val _userAdd = MutableStateFlow(SimpleResponse.initial<Content?>())
    val userAdd: StateFlow<SimpleResponse<Content?>> = _userAdd.asStateFlow()


    fun resetToast() {
        _userError.value = null

    }

    fun resetAccountValue() {

        _userAdd.value = SimpleResponse.initial()
    }


    fun addNewUser(content: Content) {

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = dataRepository.addUser(content)

                if (response) {
                    _userAdd.value = SimpleResponse.success(content)


                } else {
                    val errorMessage = "Add operation failed, please try again"
                    _userError.value = errorMessage
                    _userAdd.value = SimpleResponse.failure(Exception(errorMessage))
                }


            } catch (e: Exception) {
                val errorMessage = "Operation failed, please try again"
                _userError.value = errorMessage
                _userAdd.value = SimpleResponse.failure(Exception(errorMessage))

            }


        }


    }


}