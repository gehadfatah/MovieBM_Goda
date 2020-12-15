package com.goda.movieapp.domain.repository

import androidx.lifecycle.LiveData
import com.goda.movieapp.data.local.MovieDao
import com.goda.movieapp.data.remote.Api
import com.goda.movieapp.domain.pojo.MovieDetail
import com.goda.movieapp.domain.pojo.MovieQuery
import com.goda.movieapp.domain.pojo.MovieResult
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: Api, private val movieDao: MovieDao) {

    suspend fun fetchMovies(map: Map<String, String>, queryTag: QUERYTAG): Response<MovieQuery> {
       return when(queryTag){
            QUERYTAG.SEARCH -> api.searchMovie(map)
            QUERYTAG.DISCOVER -> api.popularMovie(map)
            QUERYTAG.TRENDING -> api.trendingMovie(map["time_window"] ?: error("week"), map)
        }
    }

    suspend fun movieDetail(movieId: Long, query: String): Response<MovieDetail> {
        return api.movieDetail(movieId, query)
    }

    fun allFavoriteMovie(): LiveData<List<MovieResult>> {
        return movieDao.allFavorite()
    }

    suspend fun insert(movieResult: MovieResult) {
        return movieDao.insertFavorite(movieResult)
    }

    suspend fun update(movieResult: MovieResult) {
        return movieDao.updateFavorite(movieResult)
    }

    suspend fun delete(movieResult: MovieResult) {
        return movieDao.deleteFavorite(movieResult)
    }

    fun existAsFavorite(id: String): LiveData<List<MovieResult>> {
        return movieDao.existAsFavorite(id)
    }

    enum class QUERYTAG {
        SEARCH, DISCOVER, TRENDING
    }

}