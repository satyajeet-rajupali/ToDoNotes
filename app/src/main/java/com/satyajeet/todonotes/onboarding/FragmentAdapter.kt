package com.satyajeet.todonotes.onboarding

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jetbrains.annotations.NotNull
import androidx.fragment.app.Fragment as Fragment

class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(@NotNull position: Int): Fragment {
        if(position==0){
           return OnBoardingOneFragment()
        } else {
            return OnBoardingTwoFragment()
        }
    }
}