package com.onlineShop.shopit.ui.activities

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.ActivitySoldProductDetailsBinding
import com.onlineShop.shopit.models.SoldProduct
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SoldProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoldProductDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySoldProductDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        var productDetails: SoldProduct = SoldProduct()
        if (intent.hasExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS)) {
            productDetails = intent.getParcelableExtra<SoldProduct>(
                Constants.EXTRA_SOLD_PRODUCT_DETAILS
            )!!
        }
        setupActionBar()
        setupUi(productDetails)
    }


    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarSoldProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarSoldProductDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }


    private fun setupUi(productDetails: SoldProduct) {
        binding.tvSoldProductDetailsId.text = productDetails.id
        val dateFormat = "dd MMM yyyy HH:mm"

        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = productDetails.order_date
        binding.tvSoldProductDetailsDate.text = formatter.format(calendar.time)


        GlideLoader(this@SoldProductDetailsActivity).loadProductPicture(
            productDetails.image,
            binding.ivProductItemImage
        )

        binding.tvProductItemName.text = productDetails.title
        binding.tvProductItemPrice.text = productDetails.price
        binding.tvSoldProductQuantity.text = productDetails.sold_quantity


        binding.tvSoldDetailsAddressType.text = productDetails.address.type
        binding.tvSoldDetailsFullName.text = productDetails.address.name
        binding.tvSoldDetailsAddress.text =
            "${productDetails.address.address}, ${productDetails.address.zipCode}"
        binding.tvSoldDetailsAdditionalNote.text = productDetails.address.additionalNote

        if (productDetails.address.otherDetails.isNotEmpty()) {
            binding.tvSoldDetailsOtherDetails.visibility = View.VISIBLE
            binding.tvSoldDetailsOtherDetails.text = productDetails.address.otherDetails
        } else {
            binding.tvSoldDetailsOtherDetails.visibility = View.GONE
        }
        binding.tvSoldDetailsMobileNumber.text = productDetails.address.mobileNumber

        binding.tvSoldProductSubTotal.text = productDetails.sub_total_amount
        binding.tvSoldProductShippingCharge.text = productDetails.shipping_charge
        binding.tvSoldProductTotalAmount.text = productDetails.total_amount


    }
}