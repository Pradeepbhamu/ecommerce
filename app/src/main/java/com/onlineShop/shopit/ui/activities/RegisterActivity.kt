package com.onlineShop.shopit.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.onlineShop.shopit.R

import com.onlineShop.shopit.databinding.ActivityRegisterBinding
import com.onlineShop.shopit.firestore.FirestoreClass
import com.onlineShop.shopit.models.User

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        binding.tvLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            registerUser()

        }


    }


    private fun validateRegisterDetail(): Boolean {

        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_confirm_password),
                    true
                )
                false
            }

            binding.etPassword.text.toString()
                .trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                    true
                )
                false
            }

            else -> {
                true
            }

        }


    }


    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarRegisterActivity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24)

        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

    }

    private fun registerUser() {

        // Check with validate function if the entries are valid or not.
        if (validateRegisterDetail()) {

            showProgressDialog(resources.getString(R.string.please_wait))
            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!


                            val user = User(
                                firebaseUser.uid,
                                binding.etFirstName.text.toString().trim { it <= ' ' },
                                binding.etLastName.text.toString().trim { it <= ' ' },
                                binding.etEmail.text.toString().trim { it <= ' ' }
                            )

                            FirestoreClass().registerUser(this@RegisterActivity, user)

//                            FirebaseAuth.getInstance().signOut()
//                            finish()
                        } else {
                            hideProgressDialog()

                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }


    fun userRegistrationSuccess() {


        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()



        FirebaseAuth.getInstance().signOut()

        finish()
    }

}