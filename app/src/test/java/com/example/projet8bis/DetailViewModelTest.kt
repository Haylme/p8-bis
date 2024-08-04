package com.example.projet8bis

import com.example.projet8bis.data.DataRepository
import com.example.projet8bis.model.SimpleResponse
import com.example.projet8bis.ui.detail.DetailViewModel
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
class DetailViewModelTest {

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var detailViewModel: DetailViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        detailViewModel = DetailViewModel(dataRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `translateDate should update translate on success`() = runBlocking {
        val input = 123.45
        val expectedResult = 678.90

        `when`(dataRepository.fetchTranslate(input)).thenReturn(expectedResult)

        detailViewModel.translateDate(input)
        testScope.advanceUntilIdle()

        val translateState = detailViewModel.translate.first()
        assertEquals(SimpleResponse.success(expectedResult), translateState)
    }


}
