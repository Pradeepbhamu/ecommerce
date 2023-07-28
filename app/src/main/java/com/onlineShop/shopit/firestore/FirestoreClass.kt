package com.onlineShop.shopit.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.onlineShop.shopit.models.Product
import com.onlineShop.shopit.ui.activities.LoginActivity
import com.onlineShop.shopit.ui.activities.RegisterActivity
import com.onlineShop.shopit.ui.activities.UserProfileActivity
import com.onlineShop.shopit.models.User
import com.onlineShop.shopit.ui.activities.AddProductActivity
import com.onlineShop.shopit.ui.activities.SettingsActivity
import com.onlineShop.shopit.utils.Constants
import androidx.fragment.app.Fragment
import com.google.android.play.core.integrity.e
import com.google.firebase.firestore.ktx.toObject
import com.onlineShop.shopit.models.Address
import com.onlineShop.shopit.models.CartItem
import com.onlineShop.shopit.models.Order
import com.onlineShop.shopit.models.SoldProduct
import com.onlineShop.shopit.ui.activities.AddEditAddressActivity
import com.onlineShop.shopit.ui.activities.AddressListActivity
import com.onlineShop.shopit.ui.activities.CartListActivity
import com.onlineShop.shopit.ui.activities.CheckoutActivity
import com.onlineShop.shopit.ui.activities.ProductDetailsActivity
import com.onlineShop.shopit.ui.fragments.DashboardFragment
import com.onlineShop.shopit.ui.fragments.OrdersFragment
import com.onlineShop.shopit.ui.fragments.ProductsFragment
import com.onlineShop.shopit.ui.fragments.SoldProductFragment

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()


    fun registerUser(activity: RegisterActivity, userInfo: User) {

        mFirestore.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }


    }

    fun getCurrentUserId(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId

    }

    fun getUserDetails(activity: Activity) {

        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(User::class.java)!!


                val sharedPreferences = activity.getSharedPreferences(
                    Constants.SHOPIT_PREFERENCES,
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME, "${user.firstName} ${user.lastName}"
                )
                editor.apply()




                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)

                    }

                    is SettingsActivity -> {
                        activity.userDetailsSuccess(user)
                    }

                }

            }
            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }

                    is SettingsActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS).document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {

                        activity.userProfileUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->

                when (activity) {
                    is UserProfileActivity -> {
                        // Hide the progress dialog if there is any error. And print the error in log.
                        activity.hideProgressDialog()
                    }


                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the user details.",
                    e
                )
            }


    }

    fun uploadProductDetails(activity: AddProductActivity, productInfo: Product) {

        mFirestore.collection(Constants.PRODUCTS)
            .document()
            .set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading the product details",
                    e
                )
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {


        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI!!
            )
        )


        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->

                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )


                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())


                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }

                            is AddProductActivity -> {
                                activity.imageUploadSuccess(uri.toString())

                            }
                        }
                        // END
                    }
            }
            .addOnFailureListener { exception ->


                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }

                    is AddProductActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun getProductsList(fragment: Fragment) {

        mFirestore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->


                Log.e("Products List", document.documents.toString())


                val productsList: ArrayList<Product> = ArrayList()


                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    fun getDashboardItemsList(fragment: DashboardFragment) {

        mFirestore.collection(Constants.PRODUCTS)
            .get()
            .addOnSuccessListener { document ->


                Log.e(fragment.javaClass.simpleName, document.documents.toString())


                val productsList: ArrayList<Product> = ArrayList()


                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    productsList.add(product)
                }


                fragment.successDashboardItemsList(productsList)
            }
            .addOnFailureListener { e ->

                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }

    fun checkIfItemExistInCart(activity: ProductDetailsActivity, productId: String) {
        mFirestore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .whereEqualTo(Constants.PRODUCT_ID, productId)
            .get()
            .addOnSuccessListener { document ->
                Log.e(
                    activity.javaClass.simpleName, document.documents.toString()
                )

                if (document.documents.size > 0) {
                    activity.productExitsInCart()
                } else {
                    activity.hideProgressDialog()
                }


            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while checking the existing cart list",
                    e
                )
            }

    }

    fun addCartItems(activity: ProductDetailsActivity, addToCart: CartItem) {
        mFirestore.collection(Constants.CART_ITEMS)
            .document()
            .set(addToCart, SetOptions.merge())
            .addOnSuccessListener {
                activity.addToCartSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while creating the document for cart item",
                    e
                )

            }
    }

    //fun for downloading the cart list

    fun getCartList(activity: Activity) {
        mFirestore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val list: ArrayList<CartItem> = ArrayList()
                for (i in document.documents) {
                    val cartItem = i.toObject(CartItem::class.java)
                    if (cartItem != null) {
                        cartItem.id = i.id
                    }
                    list.add(cartItem!!)
                }
                when (activity) {
                    is CartListActivity -> {

                        activity.successCartItemList(list)
                    }

                    is CheckoutActivity -> {
                        activity.successCartItemList(list)
                    }
                }
            }
            .addOnFailureListener {

                    e ->
                when (activity) {
                    is CartListActivity -> {
                        activity.hideProgressDialog()

                    }

                    is CheckoutActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting the cart list item",
                    e
                )
            }
    }

    fun getAllProductList(activity: Activity) {

        mFirestore.collection(Constants.PRODUCTS)
            .get()

            .addOnSuccessListener { document ->

                Log.e("Product List", document.documents.toString())
                val productsList: ArrayList<Product> = ArrayList()
                for (i in document.documents) {
                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id
                    productsList.add(product)

                }
                when (activity) {
                    is CartListActivity -> {
                        activity.successProductListFromFireStore(productsList)
                    }

                    is CheckoutActivity -> {
                        activity.successProductsListFromFirestore(productsList)
                    }
                }

            }
            .addOnFailureListener { e ->
                when (activity) {
                    is CartListActivity -> {
                        activity.hideProgressDialog()
                    }

                    is CheckoutActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e("Get Product List", "Error while getting all product list", e)
            }

    }

    fun deleteProduct(fragment: ProductsFragment, productId: String) {

        mFirestore.collection(Constants.PRODUCTS)
            .document(productId)
            .delete()
            .addOnSuccessListener {


                fragment.productDeleteSuccess()

            }
            .addOnFailureListener { e ->


                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Error while deleting the product.",
                    e
                )
            }
    }


    fun removeItemFromCart(context: Context, cart_id: String) {


        mFirestore.collection(Constants.CART_ITEMS)
            .document(cart_id) // cart id
            .delete()
            .addOnSuccessListener {


                when (context) {
                    is CartListActivity -> {
                        context.itemRemovedSuccess()
                    }
                }

            }
            .addOnFailureListener { e ->


                when (context) {
                    is CartListActivity -> {
                        context.hideProgressDialog()
                    }
                }
                Log.e(
                    context.javaClass.simpleName,
                    "Error while removing the item from the cart list.",
                    e
                )
            }
    }


    fun updateMyCart(context: Context, cart_id: String, itemHashMap: HashMap<String, Any>) {

        mFirestore.collection(Constants.CART_ITEMS)
            .document(cart_id)
            .update(itemHashMap)
            .addOnSuccessListener {
                when (context) {
                    is CartListActivity -> {
                        context.itemUpdateSuccess()
                    }
                }
            }
            .addOnFailureListener { e ->
                when (context) {
                    is CartListActivity -> {
                        context.hideProgressDialog()
                    }
                }
                Log.e(
                    context.javaClass.simpleName,
                    "Error while updating the cart item.",
                    e
                )
            }
    }

    fun addAddress(activity: AddEditAddressActivity, addressInfo: Address) {


        mFirestore.collection(Constants.ADDRESSES)
            .document()
            .set(addressInfo, SetOptions.merge())
            .addOnSuccessListener {


                activity.addUpdateAddressSuccess()

            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while adding the address.",
                    e
                )
            }
    }


    fun getProductDetails(activity: ProductDetailsActivity, productId: String) {
        mFirestore.collection(Constants.PRODUCTS)
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())
                val product = document.toObject(Product::class.java)

                if (product != null) {
                    activity.productDetailsSuccess(product)
                }

            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while getting the product details", e)
            }

    }

    fun getAddressesList(activity: AddressListActivity) {

        mFirestore.collection(Constants.ADDRESSES)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.documents.toString())
                val addressList: ArrayList<Address> = ArrayList()
                for (i in document.documents) {
                    val address = i.toObject(Address::class.java)!!
                    address.id = i.id
                    addressList.add(address)
                }
                activity.successAddressListFromFirestore(addressList)
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while getting the address list.", e)
            }

    }

    fun updateAddress(activity: AddEditAddressActivity, addressInfo: Address, addressId: String) {

        mFirestore.collection(Constants.ADDRESSES)
            .document(addressId)
            .set(addressInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.addUpdateAddressSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while updating the Address.",
                    e
                )
            }
    }

    fun deleteAddress(activity: AddressListActivity, addressId: String) {

        mFirestore.collection(Constants.ADDRESSES)
            .document(addressId)
            .delete()
            .addOnSuccessListener {
                activity.deleteAddressSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while deleting the address.",
                    e
                )
            }
    }

    fun placeOrder(activity: CheckoutActivity, order: Order) {

        mFirestore.collection(Constants.ORDERS)
            .document()
            .set(order, SetOptions.merge())
            .addOnSuccessListener {
                activity.orderPlacedSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while placing an order.",
                    e
                )
            }
    }

    fun updateAllDetails(activity: CheckoutActivity, cartList: ArrayList<CartItem>,order :Order) {

        val writeBatch = mFirestore.batch()

        for (cartItem in cartList) {
//            val productHashMap = HashMap<String, Any>()
//            productHashMap[Constants.STOCK_QUANTITY] =
//                (cartItem.stock_quantity.toInt() - cartItem.cart_quantity.toInt()).toString()


            val soldProduct =SoldProduct(
                cartItem.product_owner_id,
                cartItem.title,
                cartItem.price,
                cartItem.cart_quantity,
                cartItem.image,
                order.title,
                order.order_datetime,
                order.sub_total_amount,
                order.shipping_charge,
                order.total_amount,
                order.address
            )

            val documentReference = mFirestore.collection(Constants.SOLD_PRODUCTS)
                .document(cartItem.product_id)

            writeBatch.set(documentReference, soldProduct)
        }

        for (cart in cartList) {

            val documentReference = mFirestore.collection(Constants.CART_ITEMS)
                .document(cart.id)
            writeBatch.delete(documentReference)
        }
        writeBatch.commit().addOnSuccessListener {

            activity.allDetailsUpdatedSuccessfully()

        }.addOnFailureListener { e ->
            activity.hideProgressDialog()

            Log.e(
                activity.javaClass.simpleName,
                "Error while updating all the details after order placed.",
                e
            )
        }
    }

    fun getMyOrderList(fragment: OrdersFragment){
     mFirestore.collection(Constants.ORDERS)
         .whereEqualTo(Constants.USER_ID,getCurrentUserId())
         .get()
         .addOnSuccessListener {
             document->
             val list:ArrayList<Order> =ArrayList()

             for(i in document){
                 val orderItem =i.toObject(Order::class.java)
                 orderItem.id=i.id
                 list.add(orderItem)
             }
             fragment.populateOrdersListInUi(list)
         }
         .addOnFailureListener {
             e->
            fragment.hideProgressDialog()
            Log.e(fragment.javaClass.simpleName,"Error while getting the order list",e)
         }
    }


    fun getSoldProductsList(fragment: SoldProductFragment){
        mFirestore.collection(Constants.SOLD_PRODUCTS)
            .whereEqualTo(Constants.USER_ID,getCurrentUserId())
            .get()
            .addOnSuccessListener {
                document->

               val list :ArrayList<SoldProduct> = ArrayList()
                for(i in document)
                {
                    val soldProduct = i.toObject(SoldProduct::class.java)
                    soldProduct.id=i.id
                    list.add(soldProduct)
                }
                fragment.successSoldProductsList(list)


            }
            .addOnFailureListener {
                e->
                fragment.hideProgressDialog()
                Log.e(fragment.javaClass.simpleName,"Error while getting the list of  sold products",e)
            }
    }

}
