package com.wiryadev.bajpmovie.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.snackbar.Snackbar
import com.wiryadev.bajpmovie.BuildConfig
import com.wiryadev.bajpmovie.R
import com.wiryadev.bajpmovie.data.source.local.entity.MovieEntity
import com.wiryadev.bajpmovie.data.source.local.entity.TvEntity
import com.wiryadev.bajpmovie.databinding.ActivityDetailBinding
import com.wiryadev.bajpmovie.databinding.ContentDetailBinding
import com.wiryadev.bajpmovie.utils.Constants
import com.wiryadev.bajpmovie.utils.Constants.Companion.PLACEHOLDER
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_MOVIE
import com.wiryadev.bajpmovie.utils.Constants.Companion.TYPE_TV
import com.wiryadev.bajpmovie.utils.invisible
import com.wiryadev.bajpmovie.utils.visible
import com.wiryadev.bajpmovie.viewmodel.ViewModelFactory
import com.wiryadev.bajpmovie.vo.Status

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_TYPE = "extra_type"
    }

    private lateinit var contentDetailBinding: ContentDetailBinding
    private lateinit var activityDetailBinding: ActivityDetailBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        contentDetailBinding = activityDetailBinding.detailContent
        setContentView(activityDetailBinding.root)
        setSupportActionBar(activityDetailBinding.toolbar)

        val type = intent.getStringExtra(EXTRA_TYPE)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        if (type.equals(TYPE_MOVIE)) {
            intent.getParcelableExtra<MovieEntity>(EXTRA_DETAIL)?.let { showMovie(it) }
        } else if (type.equals((TYPE_TV))) {
            intent.getParcelableExtra<TvEntity>(EXTRA_DETAIL)?.let { showTv(it) }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        with(activityDetailBinding) {
            if (state) {
                fab.setImageResource(R.drawable.ic_round_favorite_24)
            } else {
                fab.setImageResource(R.drawable.ic_round_favorite_border_24)
            }
        }
    }

    private fun showSnackBar(state: Boolean) {
        if (state) {
            Snackbar.make(
                contentDetailBinding.root,
                getString(R.string.added),
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                contentDetailBinding.root,
                getString(R.string.removed),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun showMovie(movie: MovieEntity) {
        supportActionBar?.title = movie.title
        contentDetailBinding.tvDetailTitle.text = movie.title
        contentDetailBinding.tvDetailOverview.text = movie.overview
        contentDetailBinding.tvLabelDate.text = getString(R.string.release_date)
        contentDetailBinding.tvDetailDate.text = movie.releaseDate

        val score = (movie.voteAverage * 10).toInt()
        contentDetailBinding.tvDetailScore.text =
            activityDetailBinding.root.resources.getString(R.string.score, score.toString())

        val poster = BuildConfig.BASE_IMAGE_URL + movie.posterPath
        contentDetailBinding.ivDetailPoster.load(poster) {
            placeholder(PLACEHOLDER)
            transformations(RoundedCornersTransformation(Constants.CORNER_RADIUS))
        }

        val backdrop = BuildConfig.BASE_IMAGE_URL + movie.backdropPath
        activityDetailBinding.ivDetailBackdrop.load(backdrop)

        getGenre(TYPE_MOVIE, movie.genreIds)

        viewModel.checkFavoriteMovie(movie.id).observe(this, {
            activityDetailBinding.fab.setOnClickListener {
                val newState = !movie.isFavorite
                viewModel.setFavoriteMovie(movie, newState)
                showSnackBar(newState)
            }
            setFavoriteState(it)
        })
    }

    private fun showTv(tv: TvEntity) {
        supportActionBar?.title = tv.name
        contentDetailBinding.tvDetailTitle.text = tv.name
        contentDetailBinding.tvDetailOverview.text = tv.overview
        contentDetailBinding.tvLabelDate.text = getString(R.string.airing_date)
        contentDetailBinding.tvDetailDate.text = tv.firstAirDate

        val score = (tv.voteAverage * 10).toInt()
        contentDetailBinding.tvDetailScore.text =
            activityDetailBinding.root.resources.getString(R.string.score, score.toString())

        val poster = BuildConfig.BASE_IMAGE_URL + tv.posterPath
        contentDetailBinding.ivDetailPoster.load(poster) {
            placeholder(PLACEHOLDER)
            transformations(RoundedCornersTransformation(Constants.CORNER_RADIUS))
        }

        val backdrop = BuildConfig.BASE_IMAGE_URL + tv.backdropPath
        activityDetailBinding.ivDetailBackdrop.load(backdrop)

        getGenre(TYPE_TV, tv.genreIds)

        viewModel.checkFavoriteTv(tv.id).observe(this, {
            activityDetailBinding.fab.setOnClickListener {
                val newState = !tv.isFavorite
                viewModel.setFavoriteTv(tv, newState)
                showSnackBar(newState)
            }
            setFavoriteState(it)
        })
    }

    private fun getGenre(type: String, genreIds: List<Int>) {
        when (type) {
            TYPE_MOVIE -> {
                viewModel.getGenreMovie().observe(this, { genres ->
                    val genre = ArrayList<String>()

                    genres?.let {
                        when (genres.status) {
                            Status.LOADING -> activityDetailBinding.progressBar.visible()
                            Status.SUCCESS -> {
                                activityDetailBinding.progressBar.invisible()
                                genres.data?.map {
                                    if (it.genreId in genreIds) {
                                        genre.add(it.name)
                                    }
                                }
                            }
                            Status.ERROR -> {
                                activityDetailBinding.progressBar.invisible()
                            }
                        }
                    }
                    contentDetailBinding.tvDetailGenre.text = genre.joinToString()
                })
            }

            TYPE_TV -> {
                viewModel.getGenreTv().observe(this, { genres ->
                    val genre = ArrayList<String>()

                    genres?.let {
                        when (genres.status) {
                            Status.LOADING -> activityDetailBinding.progressBar.visible()
                            Status.SUCCESS -> {
                                activityDetailBinding.progressBar.invisible()
                                genres.data?.map {
                                    if (it.genreId in genreIds) {
                                        genre.add(it.name)
                                    }
                                }
                            }
                            Status.ERROR -> {
                                activityDetailBinding.progressBar.invisible()
                            }
                        }
                    }
                    contentDetailBinding.tvDetailGenre.text = genre.joinToString()
                })
            }

            else -> contentDetailBinding.tvDetailGenre.text = getString(R.string.unspecified_genre)
        }
    }

}