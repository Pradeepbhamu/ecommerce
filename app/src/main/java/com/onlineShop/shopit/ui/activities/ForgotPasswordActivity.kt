package com.onlineShop.shopit.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.onlineShop.shopit.R
import com.onlineShop.shopit.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()




    }



    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarForgotPasswordActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        }
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnSubmit.setOnClickListener {
            val email=binding.etForgotEmail.text.toString().trim { it <= ' ' }
            if(email.isEmpty())
            {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email),true)
            }
            else
            {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        task->
                        hideProgressDialog()
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this@ForgotPasswordActivity,R.string.email_sent_success,Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else
                        {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }

            }


        }

    }

}