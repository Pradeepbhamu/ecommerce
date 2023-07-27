package com.onlineShop.shopit.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.ui.activities.ProductDetailsActivity
import com.onlineShop.shopit.ui.fragments.ProductsFragment
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader

open class MyProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment
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


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {


            val tvItemName = holder.itemView.findViewById<TextView>(R.id.tv_item_name)
            tvItemName.text = model.title

            val tvItemPrice = holder.itemView.findViewById<TextView>(R.id.tv_item_price)
            tvItemPrice.text = "$${model.price}"

            val ivItemImage = holder.itemView.findViewById<ImageView>(R.id.iv_item_image)
            GlideLoader(context).loadProductPicture(model.image, ivItemImage)

            val ibDeleteProduct = holder.itemView.findViewById<ImageButton>(R.id.ib_delete_product)
            ibDeleteProduct.setOnClickListener {


                fragment.deleteProduct(model.product_id)

            }

            holder.itemView.setOnClickListener {
                val intent=Intent(context,ProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PRODUCT_ID,model.product_id)
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID,model.user_id)
                context.startActivity(intent)
            }

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}