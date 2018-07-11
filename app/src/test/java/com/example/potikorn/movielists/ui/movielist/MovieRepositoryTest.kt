package com.example.potikorn.movielists.ui.movielist

import com.example.potikorn.movielists.JsonMockUtility
import com.example.potikorn.movielists.RxSchedulersOverrideRule
import com.example.potikorn.movielists.base.BaseSubscriber
import com.example.potikorn.movielists.httpmanager.MovieApi
import com.example.potikorn.movielists.remote.RemoteFilmDataSource
import com.example.potikorn.movielists.repository.MovieRepository
import com.example.potikorn.movielists.room.Film
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner
import retrofit2.Response

@RunWith(PowerMockRunner::class)
class MovieRepositoryTest {

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulersOverrideRule()

    @Mock
    lateinit var mockCallback: BaseSubscriber.SubscribeCallback<Film>

    @Mock
    lateinit var movieApi: MovieApi

    private var jsonUtil = JsonMockUtility()
    private val remoteFilmDataSource: RemoteFilmDataSource
    private val spy: RemoteFilmDataSource
    private val repository: MovieRepository
    private var responseBody: ResponseBody = ResponseBody.create(MediaType.parse("application/json"), "")

    init {
        MockitoAnnotations.initMocks(this)
        remoteFilmDataSource = RemoteFilmDataSource(movieApi)
        spy = remoteFilmDataSource
        repository = MovieRepository(remoteFilmDataSource)
    }

    @Test
    fun getFilmListShouldSuccess() {
        val mockResult = jsonUtil.getJsonToMock("film_list_response.json", Film::class.java)
        val mockResponse = Response.success(mockResult)
        val mockObservable = Single.just(mockResponse)
        Mockito.`when`(spy.requestNowPlayingList(Mockito.anyInt())).thenReturn(mockObservable)
        val testObserver = mockObservable.test()
        repository.getNowPlayingList(Mockito.anyInt(), mockCallback)
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { return@assertValue it == mockResponse }
    }

    @Test
    fun getFilmListShouldNotFound() {
        val mockResponse = Response.error<Film>(404, responseBody)
        val mockObservable = Single.just(mockResponse)
        Mockito.`when`(spy.requestNowPlayingList(Mockito.anyInt())).thenReturn(mockObservable)
//        presenter.getMyFavoriteBlog()
        val testObserver = mockObservable.test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.assertValue { it.code() == 404 }
//        Mockito.verify(mockView, Mockito.times(1)).hideProgressDialog()
//        Mockito.verify(mockView, Mockito.times(1)).showError(mockResponse.message())
//        Mockito.verify(mockView, Mockito.times(1)).setEmptyList(true)
    }
}