package com.onlineShop.shopit.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.FragmentBaseBinding


open class BaseFragment : Fragment() {


    private lateinit var binding: FragmentBaseBinding
    private lateinit var mProgressDialog:Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }
    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(requireActivity())

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress)

        val tvProgressText = mProgressDialog.findViewById<TextView>(R.id.tv_progress_text)
        tvProgressText.text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressDialog.show()
    }



    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
    }
