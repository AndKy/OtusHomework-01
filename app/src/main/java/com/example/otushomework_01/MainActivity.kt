package com.example.otushomework_01

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity
    : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMovieList()
        initPager()
        initClickListeners()
    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is MovieListFragment) {
            attachFragment(fragment)
        }
        else if (fragment is FavoritesFragment) {
            attachFragment(fragment)
        }
    }

    private fun attachFragment(fragment: MovieListFragment) {
        // set movies list
        fragment.movies = Application.getMovies()

        // subscribe
        fragment.listener = object : MovieListFragment.Listener {
            override fun onMovieSwipeDelete(movieItem: MovieItem) {
                Application.removeMovie(movieItem)
            }

            override fun onMovieClick(movieItem: MovieItem) {
                Application.setSelectedMovie(movieItem)
            }

            override fun onMovieFavButtonClick(movieItem: MovieItem) {
                Application.favMovieToggle(movieItem)
                Application.setSelectedMovie(movieItem)

                val msg =
                    if (movieItem.isFavorite)
                        getString(R.string.movie_added_to_favorite).format(movieItem.textTitle.replace("\n", " "))
                    else
                        getString(R.string.movie_removed_from_favorites).format(movieItem.textTitle.replace("\n", " "))

                Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            }

            override fun onMovieDetailsButtonClick(movieItem: MovieItem) {
                openDetailsWindow(movieItem)
            }

            override fun onAddMovieButtonClick() {
                Application.addNewMovie()
            }
        }

        // attach fragment to data model
        Application.addListener(ApplicationUtils.makeListenerFor(fragment))
    }

    private fun attachFragment(fragment: FavoritesFragment) {
        // set movies list
        fragment.movies = Application.getFavMovies()

        // subscribe
        fragment.listener = object : FavoritesFragment.Listener {
            override fun onFavMovieClick(movieItem: MovieItem) {
                Application.setSelectedMovie(movieItem)
                viewpager.setCurrentItem(PAGE_MOVIES, true)
            }
        }

        // attach fragment to data model
        Application.addListener(ApplicationUtils.makeListenerFor(fragment))
    }

    override fun onDestroy() {
        Application.clearListeners()
        super.onDestroy()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OUR_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val comment = it.getStringExtra(DetailsActivity.STATE_COMMENT)
                    val like = it.getBooleanExtra(DetailsActivity.STATE_LIKE, false)

                    log("onActivityResult: comment='%s'".format(comment))
                    log("onActivityResult: like='%b'".format(like))
                }
            }
        }
    }

    private fun openDetailsWindow(movieItem: MovieItem) {
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(STATE_SELECTED_MOVIE, movieItem)
        }

        startActivityForResult(intent, OUR_REQUEST_CODE)
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
        buttonDayNight.setOnClickListener {
            setNightMode(!isNightMode())
        }
    }

    private fun initMovieList() {
        if (Application.getMovies().isEmpty()) {
            arrayOf(
                MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, getString(R.string.movie_1_title), getString(R.string.movie_1_desc), getString(R.string.movie_1_about)),
                MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, getString(R.string.movie_2_title), getString(R.string.movie_2_desc), getString(R.string.movie_2_about)),
                MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, getString(R.string.movie_3_title), getString(R.string.movie_3_desc), getString(R.string.movie_3_about))
            ).forEach { Application.addMovie(it) }

            // Add number of random movies
            repeat(9) {
                Application.addNewMovie()
            }
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

    companion object {
        const val STATE_SELECTED_MOVIE = "selected-movie"
        const val OUR_REQUEST_CODE = 1
        const val PAGE_MOVIES = 0
        const val PAGE_FAVORITES = 1
    }
}
