package com.onlineShop.shopit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.FragmentOrdersBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.Order
import com.onlineShop.shopit.ui.adapters.MyOrdersListAdapter

class OrdersFragment : BaseFragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)




        return binding.root
    }

    fun populateOrdersListInUi(ordersList:ArrayList<Order>)
    {
        hideProgressDialog()
        if(ordersList.size>0)
        {
            binding.rvMyOrderItems.visibility=View.VISIBLE
            binding.tvNoOrdersFound.visibility=View.GONE

            binding.rvMyOrderItems.layoutManager=LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)

            val myOrderAdapter=MyOrdersListAdapter(requireActivity(),ordersList)
            binding.rvMyOrderItems.adapter=myOrderAdapter


        }
        else{
            binding.rvMyOrderItems.visibility=View.GONE
            binding.tvNoOrdersFound.visibility=View.VISIBLE
        }


    }

    private fun getMyOrdersList(){
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getMyOrderList(this@OrdersFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getMyOrdersList()
    }
}


//val root: View = binding.root