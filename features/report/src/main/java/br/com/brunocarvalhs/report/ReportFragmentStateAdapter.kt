package br.com.brunocarvalhs.report

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ReportFragmentStateAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val arrayList = ArrayList<Fragment>()

    fun addFragment(fragment: Fragment?) {
        arrayList.add(fragment!!)
    }

    override fun getItemCount(): Int = arrayList.size


    override fun createFragment(position: Int): Fragment = arrayList[position]
}