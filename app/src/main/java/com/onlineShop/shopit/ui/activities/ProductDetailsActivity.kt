package com.onlineShop.shopit.ui.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.ActivityProductDetailsBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.CartItem
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader

class ProductDetailsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProductDetailsBinding

    private var mProductId: String = ""

    private lateinit var mProductDetails: Product
    private var mProductOwnerId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddToCart.setOnClickListener(this)
       binding.btnGoToCart.setOnClickListener{
            val intent = Intent(this@ProductDetailsActivity, CartListActivity::class.java)
            startActivity(intent)

        }

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!

        }

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            mProductOwnerId = intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        if (FirestoreClass().getCurrentUserId() == mProductOwnerId) {
            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
        }
        getProductDetails()
    }


    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductDetails(this, mProductId)
    }

    fun productExitsInCart() {
        hideProgressDialog()
        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }

    fun productDetailsSuccess(product: Product) {
        mProductDetails = product
//        hideProgressDialog()
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )
        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsAvailableQuantity.text = product.stock_quantity

        if (product.stock_quantity.toInt() == 0) {
            hideProgressDialog()
            binding.btnAddToCart.visibility = View.GONE
            binding.tvProductDetailsAvailableQuantity.text =
                resources.getString(R.string.lbl_out_of_stock)

            binding.tvProductDetailsAvailableQuantity.setTextColor(
                ContextCompat.getColor(
                    this@ProductDetailsActivity, R.color.colorSnackBarError
                )
            )
        } else {

            if (FirestoreClass().getCurrentUserId() == product.user_id) {
                hideProgressDialog()
            } else {
                FirestoreClass().checkIfItemExistInCart(this, mProductId)
            }
        }

    }

    private fun addToCart() {

        val cartItem = CartItem(
            FirestoreClass().getCurrentUserId(),
            mProductOwnerId,
            mProductId,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().addCartItems(this@ProductDetailsActivity, cartItem)
    }

    fun addToCartSuccess() {
        hideProgressDialog()
        Toast.makeText(
            this@ProductDetailsActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        )
            .show()
        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }

    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarProductDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }


    override fun onClick(view: View?) {

        if (view != null) {
            when (view.id) {
                R.id.btn_add_to_cart -> {
                    addToCart()
                }


            }
        }
    }
}