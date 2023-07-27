package com.onlineShop.shopit.ui.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineShop.shopit.R

import com.onlineShop.shopit.databinding.ActivityCartListBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.CartItem
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.ui.adapters.CartItemListAdapter
import com.onlineShop.shopit.utils.Constants

class CartListActivity : BaseActivity() {
    private lateinit var binding: ActivityCartListBinding
    private lateinit var mProductList: ArrayList<Product>
    private lateinit var mCartListItems: ArrayList<CartItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this@CartListActivity,AddressListActivity::class.java)
            intent.putExtra(Constants.EXTRA_SELECT_ADDRESS,true)
            startActivity(intent)
        }
    }

    // method which show cartList downloading is successfully

    fun successCartItemList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()

        for (product in mProductList) {
            for (cartItem in cartList) {
                if (product.product_id == cartItem.product_id) {

                    cartItem.stock_quantity = product.stock_quantity

                    if (product.stock_quantity.toInt() == 0) {
                        cartItem.cart_quantity = product.stock_quantity
                    }
                }
            }
        }


        mCartListItems = cartList

        if (mCartListItems.size > 0) {

            binding.rvCartItemsList.visibility = View.VISIBLE
            binding.llCheckout.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.INVISIBLE


            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
            binding.rvCartItemsList.setHasFixedSize(true)
            val cartListAdapter = CartItemListAdapter(this@CartListActivity, mCartListItems,true)

            binding.rvCartItemsList.adapter = cartListAdapter

            var subTotal = 0.0
            for (item in mCartListItems) {
                val availableQuantity = item.stock_quantity.toInt()
                if (availableQuantity > 0) {

                    val price = item.price.toDouble()
                    val quantity = item.cart_quantity.toInt()
                    subTotal += (price * quantity)
                }
            }

            binding.tvSubTotal.text = "$$subTotal"

            val shippingCharge = calculateShippingCharge(mCartListItems, subTotal)


            binding.tvShippingCharge.text = "$shippingCharge"

            if (subTotal > 0) {
                binding.llCheckout.visibility = View.VISIBLE
                val total = subTotal + shippingCharge
                binding.tvTotalAmount.text = "$$total"
            } else {
                binding.llCheckout.visibility = View.GONE
            }
        } else {
            binding.rvCartItemsList.visibility = View.GONE
            binding.llCheckout.visibility = View.GONE
            binding.tvNoCartItemFound.visibility = View.VISIBLE
        }
    }


    fun successProductListFromFireStore(productsList: ArrayList<Product>) {

        hideProgressDialog()
        mProductList = productsList
        getCartItemList()

    }

    fun itemRemovedSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@CartListActivity,
            resources.getString(R.string.msg_item_removed_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getCartItemList()
    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAllProductList(this)
    }

    fun itemUpdateSuccess() {

        hideProgressDialog()

        getCartItemList()
    }

    private fun calculateShippingCharge(
        mCartListItems: ArrayList<CartItem>,
        subTotal: Double
    ): Double {
        var shippingCharge = 0.0
        shippingCharge = if (subTotal >= Constants.MIN_ORDER_AMOUNT) {
            0.0
        } else {
            (mCartListItems.size * Constants.SHIPPING_CHARGE_PER_ITEM) / 2
        }

        return shippingCharge
    }

    //methood to get the cart item

    private fun getCartItemList() {
//        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getCartList(this@CartListActivity)
    }

    override fun onResume() {
        super.onResume()
//        getCartItemList()
        getProductList()
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCartListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarCartListActivity.setNavigationOnClickListener { onBackPressed() }
    }
}