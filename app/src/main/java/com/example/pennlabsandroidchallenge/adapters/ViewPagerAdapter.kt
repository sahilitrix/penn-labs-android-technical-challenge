package com.example.pennlabsandroidchallenge.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    /* fragmentList is a list of pairs that each contain a fragment and its string name*/
    var fragmentList: ArrayList<Pair<Fragment, String>> = ArrayList()

    fun addFragment(fragmentItem: Fragment, fragmentTitle: String) {
        fragmentList.add(Pair(fragmentItem, fragmentTitle))
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position].first
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].second
    }

}