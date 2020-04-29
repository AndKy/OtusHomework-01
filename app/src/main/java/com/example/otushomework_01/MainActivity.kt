package com.example.otushomework_01

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity
    : AppCompatActivity()
    , IMainMovieActivity {

    private val movies = ArrayList<MovieItem>()
    private val favorites = ArrayList<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!loadMovieList(savedInstanceState))
            initMovieList()

        initPager()
        initClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {

            putParcelableArrayList(STATE_MOVIE_LIST, movies)
            log("onSaveInstanceState: save %d movies".format(movies.size))
        }
    }

    override fun onBackPressed() {

        val dialog : Dialog = object : Dialog(this) {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.dialog_close)

                val btnYes = findViewById<Button>(R.id.buttonYes)
                val btnNo = findViewById<Button>(R.id.buttonNo)

                btnYes.setOnClickListener() {
                    finish()
                }

                btnNo.setOnClickListener() {
                    dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun initPager() {
        val adapter =  MovieListFragmentPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewpager.adapter = adapter

        // link together pager and tabs
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))
    }

    private fun initClickListeners() {

        // Day-night scheme button
        val btn = findViewById<ImageButton>(R.id.buttonDayNight)
        btn.setOnClickListener {
            setNightMode(!isNightMode())
        }

    }

    private fun loadMovieList(savedInstanceState: Bundle?) : Boolean {
        return if (savedInstanceState != null) {
            val items = savedInstanceState.getParcelableArrayList<MovieItem>(STATE_MOVIE_LIST)
            if (items != null) {
                movies.addAll(items)
                true
            }
            else
                false
        } else {
             false
        }
    }

    private fun initMovieList() {
        movies.addAll(
            arrayListOf(
                MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, getString(R.string.movie_1_title), getString(R.string.movie_1_desc), getString(R.string.movie_1_about)),
                MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, getString(R.string.movie_2_title), getString(R.string.movie_2_desc), getString(R.string.movie_2_about)),
                MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, getString(R.string.movie_3_title), getString(R.string.movie_3_desc), getString(R.string.movie_3_about))
            )
        )

        // Add number of random movies
        repeat(9) {
            movies.add(Hollywood.makeNewMovie(movies))
        }
    }

    private fun log(msg: String) {
        Log.d("main", msg)
    }

    private fun setNightMode(enable: Boolean) {
        val mode =
            if (enable)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun isNightMode() : Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun onFavoriteMovieClick(movie: MovieItem) {
        // scroll to movies list page
        viewpager.setCurrentItem(PAGE_MOVIES, true)
        fragmentMovies().selectMovieAndScroll(movie)
    }

    override fun onToggleFavoriteMovie(movie: MovieItem) {
        movie.isFavorite = !movie.isFavorite

        val pos = favorites.indexOfFirst { it === movie }
        if (movie.isFavorite) {
            if (pos == -1) {
                favorites.add(movie)
                fragmentFavorites().onFavoriteMovieAppended()
            }
        }
        else {
            if (pos >= 0) {
                favorites.removeAt(pos)
                fragmentFavorites().onFavoriteMovieRemovedAt(pos)
            }
        }
    }

    override fun onMovieSelected(movie: MovieItem) {
        fragmentFavorites().updateMovie(movie)
    }

    override fun onMovieUnselected(movie: MovieItem) {
        fragmentFavorites().updateMovie(movie)
    }

    override fun getMovies(): ArrayList<MovieItem> {
        return movies
    }

    override fun getFavorites(): ArrayList<MovieItem> {
        return favorites
    }

    private fun fragmentMovies(): MovieListFragment {
        return supportFragmentManager.findFragmentByTag(
            "android:switcher:%d:%d".format(R.id.viewpager, PAGE_MOVIES)
        ) as MovieListFragment
    }

    private fun fragmentFavorites(): FavoritesFragment {
        return supportFragmentManager.findFragmentByTag(
            "android:switcher:%d:%d".format(R.id.viewpager, PAGE_FAVORITES)
        ) as FavoritesFragment
    }


    companion object {
        const val STATE_MOVIE_LIST = "movie-list"
        const val PAGE_MOVIES = 0
        const val PAGE_FAVORITES = 1
    }
}
