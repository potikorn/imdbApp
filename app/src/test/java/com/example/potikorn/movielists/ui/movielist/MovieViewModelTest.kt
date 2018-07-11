package com.example.potikorn.movielists.ui.movielist

import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.repository.MovieRepository
import com.example.potikorn.movielists.room.Film
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockkClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class MovieViewModelTest {

    @Mock
    private var mockCallback = Mockito.mock(BaseSubscriber.SubscribeCallback::class.java) as BaseSubscriber.SubscribeCallback<Film>

    @Mock
    private val mockRepository = mockkClass(MovieRepository::class)

    private val viewModel: MovieViewModel

    init {
        MockKAnnotations.init(this)
        viewModel = MovieViewModel(mockRepository)
    }

    @Test
    fun testViewModel() {
        every { viewModel.loadNowPlayingList() }.answers {
            mockRepository.getNowPlayingList(Mockito.anyInt(), mockCallback)
        }
        viewModel.loadNowPlayingList()

    }

}