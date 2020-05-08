package com.example.otushomework_01.ui.activities

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.otushomework_01.R
import com.example.otushomework_01.data.Application
import com.example.otushomework_01.data.Utils
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.ui.fragments.DetailsFragment
import com.example.otushomework_01.ui.fragments.FavoritesFragment
import com.example.otushomework_01.ui.fragments.MovieListFragment
import com.example.otushomework_01.ui.fragments.PagerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity
    : AppCompatActivity() {

    private var pagerFragment: PagerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // movies must be added before onCreate to avoid call fragment handlers when it not created yet
        initMovieList()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = supportFragmentManager.findFragmentByTag(PagerFragment.TAG) ?: PagerFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, fragment, PagerFragment.TAG)
            .commit()

        if (savedInstanceState == null)
            nav_view.setCheckedItem(R.id.nav_movies)

        initClickListeners()
    }

    override fun onAttachFragment(fragment: androidx.fragment.app.Fragment) {
        super.onAttachFragment(fragment)

        log("OnAttachFragment: $fragment")

        if (fragment is MovieListFragment) {
            attachFragment(fragment)
        }
        else if (fragment is FavoritesFragment) {
            attachFragment(fragment)
        }
        else if (fragment is PagerFragment) {
            attachFragment(fragment)
        }
    }

    private fun attachFragment(fragment: PagerFragment) {
        pagerFragment = fragment

        fragment.listener = object : PagerFragment.Listener {
            override fun onPageSelected(page: PagerFragment.Pages) {
                nav_view.setCheckedItem(pageToMenuItemId(page))
            }
        }
    }

    private fun attachFragment(fragment: MovieListFragment) {
        val listener = Utils.makeListenerFor(fragment)

        // set movies list
        fragment.movies = Application.getMovies()

        // subscribe
        fragment.listener = object :
            MovieListFragment.Listener {
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

            override fun onDestroy() {
                Application.removeListener(listener)
            }
        }

        // attach fragment to data model
        Application.addListener(listener)
    }

    private fun attachFragment(fragment: FavoritesFragment) {
        val listener = Utils.makeListenerFor(fragment)

        // set movies list
        fragment.movies = Application.getFavMovies()

        // subscribe
        fragment.listener = object :
            FavoritesFragment.Listener {
            override fun onFavMovieClick(movieItem: MovieItem) {
                Application.setSelectedMovie(movieItem)
                pagerFragment?.scrollToPage(PagerFragment.Pages.MOVIES)
            }

            override fun onDestroy() {
                Application.removeListener(listener)
            }
        }

        // attach fragment to data model
        Application.addListener(listener)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount == 0) {
            val dialog: Dialog = object : Dialog(this) {

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
        } else {
            super.onBackPressed()
        }
    }

    private fun openDetailsWindow(movieItem: MovieItem) {

        val detailsFragment = DetailsFragment().apply {
            movie = movieItem
            listener = object : DetailsFragment.Listener {
                override fun onInviteButtonClicked(movie: MovieItem) {
                    val inviteMsg = getString(R.string.invite_msg).format(movie.textTitle)
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, inviteMsg)
                    }

                    sendIntent.resolveActivity(packageManager)?.let {
                        startActivity(sendIntent)
                    }
                }

                override fun onReturnCommentClicked(movie: MovieItem, result: Bundle) {
                    result.let {
                        val comment = it.getString(DetailsFragment.STATE_COMMENT)
                        val like = it.getBoolean(DetailsFragment.STATE_LIKE, false)

                        log("onReturnCommentClicked: comment='%s'".format(comment))
                        log("onReturnCommentClicked: like='%b'".format(like))
                    }
                }
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, detailsFragment, DetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun initClickListeners() {
        buttonDayNight.setOnClickListener {
            setNightMode(!isNightMode())
        }

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_movies -> pagerFragment?.scrollToPage(PagerFragment.Pages.MOVIES)
                R.id.nav_favorites -> pagerFragment?.scrollToPage(PagerFragment.Pages.FAVORITES)
                R.id.nav_day_night -> setNightMode(!isNightMode())
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun initMovieList() {
        if (Application.getMovies().isEmpty()) {
            arrayOf(
                MovieItem(
                    R.drawable.movie_1_little,
                    R.drawable.movie_1_big,
                    getString(R.string.movie_1_title),
                    getString(R.string.movie_1_desc),
                    getString(R.string.movie_1_about)
                ),
                MovieItem(
                    R.drawable.movie_2_little,
                    R.drawable.movie_2_big,
                    getString(R.string.movie_2_title),
                    getString(R.string.movie_2_desc),
                    getString(R.string.movie_2_about)
                ),
                MovieItem(
                    R.drawable.movie_3_little,
                    R.drawable.movie_3_big,
                    getString(R.string.movie_3_title),
                    getString(R.string.movie_3_desc),
                    getString(R.string.movie_3_about)
                )
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
        private fun pageToMenuItemId(page: PagerFragment.Pages) : Int {
            return when(page) {
                PagerFragment.Pages.MOVIES -> R.id.nav_movies
                PagerFragment.Pages.FAVORITES -> R.id.nav_favorites
            }
        }
    }
}
