package com.onlineShop.shopit.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.onlineShop.shopit.R
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.CartItem
import com.onlineShop.shopit.ui.activities.CartListActivity
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader

open class CartItemListAdapter(
    private val context: Context,
    private val list: ArrayList<CartItem>,
    private val updateCartItems: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
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

            val ivCartItemImage = holder.itemView.findViewById<ImageView>(R.id.iv_cart_item_image)
            val tvCartItemTitle = holder.itemView.findViewById<TextView>(R.id.tv_cart_item_title)
            val tvCartItemPrice = holder.itemView.findViewById<TextView>(R.id.tv_cart_item_price)
            val tvCartQuantity = holder.itemView.findViewById<TextView>(R.id.tv_cart_quantity)
            val ibRemoveCartItem =
                holder.itemView.findViewById<ImageButton>(R.id.ib_remove_cart_item)
            val ibAddCartItem = holder.itemView.findViewById<ImageButton>(R.id.ib_add_cart_item)
            val ibDeleteCartItem =
                holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item)


            GlideLoader(context).loadProductPicture(model.image, ivCartItemImage)
            tvCartItemTitle.text = model.title
            tvCartItemPrice.text = "$${model.price}"
            tvCartQuantity.text = model.cart_quantity
            if (model.cart_quantity == "0") {
                ibRemoveCartItem.visibility = View.GONE
                ibAddCartItem.visibility = View.GONE

                if (updateCartItems) {
                    ibDeleteCartItem.visibility = View.VISIBLE
                } else {
                    ibDeleteCartItem.visibility = View.GONE
                }
                tvCartQuantity.text =
                    context.resources.getString(R.string.lbl_out_of_stock)

                tvCartQuantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            } else {
                if (updateCartItems) {
                    ibRemoveCartItem.visibility = View.VISIBLE
                    ibAddCartItem.visibility = View.VISIBLE
                    ibDeleteCartItem.visibility = View.VISIBLE
                } else {
                    ibRemoveCartItem.visibility = View.GONE
                    ibAddCartItem.visibility = View.GONE
                    ibDeleteCartItem.visibility = View.GONE
                }

                tvCartQuantity.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryText
                    )
                )
            }
            ibDeleteCartItem.setOnClickListener {
                when (context) {
                    is CartListActivity -> {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }
                }

                FirestoreClass().removeItemFromCart(context, model.id)
            }

            ibRemoveCartItem.setOnClickListener {


                if (model.cart_quantity == "1") {
                    FirestoreClass().removeItemFromCart(context, model.id)
                } else {

                    val cartQuantity: Int = model.cart_quantity.toInt()

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

                    if (context is CartListActivity) {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                }

            }
            ibAddCartItem.setOnClickListener {
                val cartQuantity: Int = model.cart_quantity.toInt()

                if (cartQuantity < model.stock_quantity.toInt()) {

                    val itemHashMap = HashMap<String, Any>()

                    itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()


                    if (context is CartListActivity) {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }

                    FirestoreClass().updateMyCart(context, model.id, itemHashMap)
                } else {
                    if (context is CartListActivity) {
                        context.showErrorSnackBar(
                            context.resources.getString(
                                R.string.msg_for_available_stock,
                                model.stock_quantity
                            ),
                            true
                        )
                    }
                }
            }
        }


    }


    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}