package com.example.otushomework_01

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MovieListFragmentPagerAdapter(
    fm: FragmentManager,
    behavior: Int,
    private val items: List<MovieItem>
) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MovieListFragment.newInstance(items)
            1 -> FavoritesFragment()
            else -> throw Exception("No such page")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}