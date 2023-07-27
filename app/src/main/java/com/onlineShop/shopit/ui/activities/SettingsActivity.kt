package com.onlineShop.shopit.ui.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.ActivitySettingsBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.User
import com.onlineShop.shopit.utils.Constants
import com.onlineShop.shopit.utils.GlideLoader

class SettingsActivity : BaseActivity() ,View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mUserDetails:User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        binding.btnLogout.setOnClickListener(this)
        binding.tvEdit.setOnClickListener(this)

        binding.llAddress.setOnClickListener(this)
    }


    private fun setUpActionBar() {

        setSupportActionBar(binding.toolbarSettingsActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }
        binding.toolbarSettingsActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun getUserDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getUserDetails(this)
    }

    fun userDetailsSuccess(user: User){
        mUserDetails=user

        hideProgressDialog()

        GlideLoader(this@SettingsActivity).loadUserPicture(user.image,binding.ivUserPhoto)
        binding.tvName.text="${user.firstName} ${user.lastName}"
        binding.tvGender.text="${user.gender}"
        binding.tvEmail.text="${user.email}"
        binding.tvMobileNumber.text="${user.mobile}"
    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    override fun onClick(view: View?) {
        if(view!=null)
        {
            when(view.id)
            {
                R.id.tv_edit ->{

                    val intent=Intent(this@SettingsActivity,UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS,mUserDetails)
                    startActivity(intent)
                }
                R.id.btn_logout ->{
                    FirebaseAuth.getInstance().signOut()
                    val intent=Intent(this@SettingsActivity,LoginActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                R.id.ll_address->{
                    val intent=Intent(this@SettingsActivity,AddressListActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}