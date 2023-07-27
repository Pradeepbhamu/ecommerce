package com.onlineShop.shopit.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.ActivityCheckoutBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.Address
import com.onlineShop.shopit.models.CartItem
import com.onlineShop.shopit.models.Order
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.ui.adapters.CartItemListAdapter
import com.onlineShop.shopit.utils.Constants

class CheckoutActivity : BaseActivity() {
    private var mAddressDetails: Address? = null
    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var mProductsList: ArrayList<Product>
    private lateinit var mCartItemsList: ArrayList<CartItem>
    private var mSubTotal: Double = 0.0
    private var mTotalAmount: Double = 0.0
    private var mShippingCharge: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails = intent.getParcelableExtra(Constants.EXTRA_SELECTED_ADDRESS)
        }
        if (mAddressDetails != null) {
            binding.tvCheckoutAddressType.text = mAddressDetails?.type
            binding.tvCheckoutFullName.text = mAddressDetails?.name
            binding.tvCheckoutAddress.text =
                "${mAddressDetails!!.address},${mAddressDetails!!.zipCode}"
            binding.tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            binding.tvCheckoutMobileNumber.text = mAddressDetails?.mobileNumber
        }
        getProductList()

        binding.btnPlaceOrder.setOnClickListener {
            placeAnOrder()
        }

    }


    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarCheckoutActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        binding.toolbarCheckoutActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    fun successProductsListFromFirestore(productList: ArrayList<Product>) {
        mProductsList = productList
        getCartItemList()
    }

    private fun getCartItemList() {
        FirestoreClass().getCartList(this@CheckoutActivity)
    }


    fun successCartItemList(cartList: ArrayList<CartItem>) {
        hideProgressDialog()
        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }
        mCartItemsList = cartList
        binding.rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        binding.rvCartListItems.setHasFixedSize(true)

        val cartListAdapter = CartItemListAdapter(this@CheckoutActivity, mCartItemsList, false)
        binding.rvCartListItems.adapter = cartListAdapter

        for (item in mCartItemsList) {

            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                mSubTotal += (price * quantity)
            }
        }

        binding.tvCheckoutSubTotal.text = "$$mSubTotal"

        mShippingCharge = calculateShippingCharge(mCartItemsList, mSubTotal)
        binding.tvCheckoutShippingCharge.text = mShippingCharge.toString()

        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            mTotalAmount = mSubTotal + mShippingCharge
            binding.tvCheckoutTotalAmount.text = "$$mTotalAmount"
        } else {
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }

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

    private fun placeAnOrder() {
        showProgressDialog(resources.getString(R.string.please_wait))

        if (mAddressDetails != null) {
            val order = Order(
                FirestoreClass().getCurrentUserId(),
                mCartItemsList,
                mAddressDetails!!,
                "My order ${System.currentTimeMillis()}",
                mCartItemsList[0].image,
                mSubTotal.toString(),
                mShippingCharge.toString(),
                mTotalAmount.toString(),
            )
            FirestoreClass().placeOrder(this, order)

        }
    }

    fun orderPlacedSuccess() {

     FirestoreClass().updateAllDetails(this,mCartItemsList)
    }
    fun allDetailsUpdatedSuccessfully() {

        hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }

    private fun getProductList() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAllProductList(this@CheckoutActivity)

    }
}