package com.example.projet8bis

import com.example.projet8bis.data.DataRepository
import com.example.projet8bis.model.Content
import com.example.projet8bis.model.SimpleResponse
import com.example.projet8bis.ui.edit.EditViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class EditViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var editViewModel: EditViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        editViewModel = EditViewModel(dataRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `editAccount should update editAdd on success`() = runBlocking {
        val content = Content(
            id = 1,
            name = "John",
            firstname = "Doe",
            phone = "1234567890",
            email = "john.doe@example.com",
            birthday = "01/01/2000",
            wage = 100.0,
            note = "Test note",
            favorite = false,
            picture = null
        )
        val userId = 1L

        `when`(dataRepository.editUser(content, userId)).thenReturn(true)

        editViewModel.editAccount(content, userId)
        testScope.advanceUntilIdle()

        val editAddState = editViewModel.editAdd.first()
        assertEquals(SimpleResponse.success(content), editAddState)
    }


}
