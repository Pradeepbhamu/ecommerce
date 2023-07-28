package com.onlineShop.shopit.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.models.SoldProduct
import com.onlineShop.shopit.ui.activities.SoldProductDetailsActivity
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader

class SoldProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<SoldProduct>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]
        if (holder is MyViewHolder) {
            val ivItemImage = holder.itemView.findViewById<ImageView>(R.id.iv_item_image)
            GlideLoader(context).loadProductPicture(model.image, ivItemImage)

            val tvItemName = holder.itemView.findViewById<TextView>(R.id.tv_item_name)
            tvItemName.text = model.title

            val tvItemPrice = holder.itemView.findViewById<TextView>(R.id.tv_item_price)
            tvItemPrice.text = "$${model.price}"

            val ibDeleteProduct = holder.itemView.findViewById<ImageButton>(R.id.ib_delete_product)

            ibDeleteProduct.visibility = View.GONE

            holder.itemView.setOnClickListener {
                val intent = Intent(context,SoldProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS,model)
                context.startActivity(intent)

            }
        }

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}