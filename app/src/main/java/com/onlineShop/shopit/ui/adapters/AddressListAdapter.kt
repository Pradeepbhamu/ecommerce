package com.onlineShop.shopit.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.models.Address
import com.onlineShop.shopit.ui.activities.AddEditAddressActivity
import com.onlineShop.shopit.ui.activities.CheckoutActivity
import com.onlineShop.shopit.utils.Constants

open class AddressListAdapter(
    private val context: Context,
    private var list: ArrayList<Address>,
    private val selectAddress :Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_address_layout,
                parent,
                false
            )
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            val tvFullName = holder.itemView.findViewById<TextView>(R.id.tv_address_full_name)
            val tvType = holder.itemView.findViewById<TextView>(R.id.tv_address_type)
            val tvDetails = holder.itemView.findViewById<TextView>(R.id.tv_address_details)
            val tvMobileNumber = holder.itemView.findViewById<TextView>(R.id.tv_address_mobile_number)

            tvFullName.text=model.name
            tvType.text=model.type
            tvDetails.text="${model.address}, ${model.zipCode}"
            tvMobileNumber.text= model.mobileNumber

            if(selectAddress){

                holder.itemView.setOnClickListener {
                   val intent=Intent(context,CheckoutActivity::class.java)
                    intent.putExtra(Constants.EXTRA_SELECTED_ADDRESS,model)
                    context.startActivity(intent)
                }
            }
        }
    }


    fun notifyEditItem(activity: Activity, position: Int) {
        val intent = Intent(context, AddEditAddressActivity::class.java)

        intent.putExtra(Constants.EXTRA_ADDRESS_DETAILS, list[position])

        activity.startActivityForResult(intent,Constants.ADD_ADDRESS_REQUEST_CODE)

        notifyItemChanged(position)
    }
    override fun getItemCount(): Int {
        return list.size
    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}