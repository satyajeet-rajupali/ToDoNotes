package com.satyajeet.todonotes.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.satyajeet.todonotes.R
import org.w3c.dom.Text


class OnBoardingTwoFragment : Fragment() {

    lateinit var next: TextView
    lateinit var back: TextView
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
        onClickListeners()
    }

    private fun onClickListeners() {
        next.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionNext()
            }
        })

        back.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                onOptionClick.onOptionBack()
            }
        })

    }

    private fun bindView(view: View) {

        next = view.findViewById(R.id.textViewNextTwo)
        back = view.findViewById(R.id.textViewBackTwo)

    }

    interface OnOptionClick{
        fun onOptionNext()
        fun onOptionBack()
    }


}