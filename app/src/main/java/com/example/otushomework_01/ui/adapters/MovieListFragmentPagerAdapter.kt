package com.example.otushomework_01.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.otushomework_01.ui.fragments.FavoritesFragment
import com.example.otushomework_01.ui.fragments.MovieListFragment

class MovieListFragmentPagerAdapter(
    fm: FragmentManager,
    behavior: Int
) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MovieListFragment()
            1 -> FavoritesFragment()
            else -> throw Exception("No such page")
        }
    }

    override fun getCount(): Int {
        return 2
    }
}