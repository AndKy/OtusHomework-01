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


class MainActivity : AppCompatActivity() {

    private val movies = ArrayList<MovieItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMovieList()
        initPager()
        initClickListeners()
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
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            movies
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

    private fun initMovieList() {
        movies.addAll(
            arrayListOf(
                MovieItem(R.drawable.movie_1_little, R.drawable.movie_1_big, getString(R.string.movie_1_title), getString(R.string.movie_1_desc), getString(R.string.movie_1_about)),
                MovieItem(R.drawable.movie_2_little, R.drawable.movie_2_big, getString(R.string.movie_2_title), getString(R.string.movie_2_desc), getString(R.string.movie_2_about)),
                MovieItem(R.drawable.movie_3_little, R.drawable.movie_3_big, getString(R.string.movie_3_title), getString(R.string.movie_3_desc), getString(R.string.movie_3_about)),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie(),
                Hollywood.makeMovie()
            )
        )
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
}
