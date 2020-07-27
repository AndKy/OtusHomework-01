package com.example.otushomework_01.ui.activities

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.otushomework_01.R
import com.example.otushomework_01.data.MovieItem
import com.example.otushomework_01.ui.fragments.DetailsFragment
import com.example.otushomework_01.ui.fragments.PagerFragment
import com.example.otushomework_01.ui.viewmodels.SharedViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pager.*

class MainActivity
    : AppCompatActivity() {

    private var pagerFragment: PagerFragment? = null
    private val model: SharedViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
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
        initModel()

    }

    private fun initModel() {
        // Scroll to main movie list on select movie
        model.selectedMovie.observe(this, Observer<MovieItem> {
            if (model.needScrollToMainPage) {
                model.needScrollToMainPage = false
                pagerFragment?.scrollToPage(PagerFragment.Pages.MOVIES)
            }
        })

        // Show snack bar when press like on movie
        model.likedMovie.observe(this, Observer<MovieItem> {
            if (it != null) {
                showCancelSnackbar(it)
                model.onCompleteActions()
            }
        })

        // Open detail activity when details button clicked
        model.detailsMovie.observe(this, Observer<MovieItem> {
            if (it != null) {
                openDetailsWindow(it)
                model.onCompleteActions()
            }
        })
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        log("OnAttachFragment: $fragment")

        if (fragment is PagerFragment) {
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

    private fun showCancelSnackbar(movieItem: MovieItem) {
        val msg =
            if (movieItem.isFavorite)
                getString(R.string.movie_added_to_favorite).format(movieItem.textTitle.replace("\n", " "))
            else
                getString(R.string.movie_removed_from_favorites).format(movieItem.textTitle.replace("\n", " "))

        Snackbar.make(viewpager, msg, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.cancel)) {
                model.onLikeCancel(movieItem)
            }
            .show()
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
