package com.wiryadev.bajpmovie.ui.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.wiryadev.bajpmovie.R

class FavoritePagerAdapter(
    private val context: Context, fm: FragmentManager
) : FragmentStatePagerAdapter(
    fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val tabTitles = arrayOf(
        R.string.movies,
        R.string.tv_shows
    )

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(tabTitles[position])


    override fun getCount(): Int = tabTitles.size

}