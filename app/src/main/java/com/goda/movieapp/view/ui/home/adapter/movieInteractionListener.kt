package com.goda.movieapp.view.ui.home.adapter

import com.goda.movieapp.domain.pojo.MovieResult

interface movieInteractionListener {
    fun onClickRetry()
    fun onMovieClick(movieResult: MovieResult, pos: Int)
}