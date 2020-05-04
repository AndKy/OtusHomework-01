package com.example.otushomework_01.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.otushomework_01.R
import com.example.otushomework_01.ui.adapters.MovieListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_pager.*

class PagerFragment : Fragment(R.layout.fragment_pager) {

    enum class Pages {
        MOVIES,
        FAVORITES
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPager()
    }

    override fun onAttachFragment(childFragment: Fragment) {
        // just pass child fragments to activity
        activity?.onAttachFragment(childFragment)
    }

    private fun initPager() {
        val adapter =
            MovieListFragmentPagerAdapter(
                childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
        viewpager.adapter = adapter

        // link together pager and tabs
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))
    }

    fun scrollToPage(page: Pages) {
        viewpager.setCurrentItem(page.ordinal, true)
    }
}