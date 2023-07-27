package com.onlineShop.shopit.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.utils.GlideLoader

open class DashboardItemsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_dashboard_layout,
                parent,
                false
            )
        )
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            val ivDashboardItemImage = holder.itemView.findViewById<ImageView>(R.id.iv_dashboard_item_image)
            val tvDashboardItemTitle = holder.itemView.findViewById<TextView>(R.id.tv_dashboard_item_title)
            val tvDashboardItemPrice = holder.itemView.findViewById<TextView>(R.id.tv_dashboard_item_price)

            GlideLoader(context).loadProductPicture(model.image, ivDashboardItemImage)
            tvDashboardItemTitle.text = model.title
            tvDashboardItemPrice.text = "$${model.price}"
            holder.itemView.setOnClickListener {
                if(onClickListener!=null)
                {
                    onClickListener!!.onClick(position,model)
                }
            }
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {


        fun onClick(position: Int, product: Product)

    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)



}