package com.onlineShop.shopit.ui.fragments

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.FragmentSoldProductBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.SoldProduct
import com.onlineShop.shopit.ui.adapters.SoldProductsListAdapter


class SoldProductFragment : BaseFragment() {
    private lateinit var _binding: FragmentSoldProductBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSoldProductBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        getSoldProductsList()
    }
    private fun getSoldProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getSoldProductsList(this@SoldProductFragment)

    }

    fun successSoldProductsList(soldProductsList: ArrayList<SoldProduct>) {
        hideProgressDialog()
        if (soldProductsList.size > 0) {

            binding.rvSoldProductItems.visibility=View.VISIBLE
            binding.tvNoSoldProductsFound.visibility=View.GONE

          binding.rvSoldProductItems.layoutManager=LinearLayoutManager(activity)
          binding.rvSoldProductItems.setHasFixedSize(true)


            val soldProductListAdapter = SoldProductsListAdapter(
                requireActivity(),soldProductsList)
            binding.rvSoldProductItems.adapter=soldProductListAdapter


        } else {
            binding.rvSoldProductItems.visibility=View.GONE
            binding.tvNoSoldProductsFound.visibility=View.VISIBLE
        }
    }
}



