// Generated by view binder compiler. Do not edit!
package com.onlineShop.shopit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.onlineShop.shopit.R;
import com.onlineShop.shopit.utils.MSPTextView;
import com.onlineShop.shopit.utils.MSPTextViewBold;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemCartLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageButton ibAddCartItem;

  @NonNull
  public final ImageButton ibDeleteCartItem;

  @NonNull
  public final ImageButton ibRemoveCartItem;

  @NonNull
  public final ImageView ivCartItemImage;

  @NonNull
  public final LinearLayout llCartItemDetails;

  @NonNull
  public final LinearLayout llCartItemImage;

  @NonNull
  public final LinearLayout llUpdateCartQuantity;

  @NonNull
  public final MSPTextViewBold tvCartItemPrice;

  @NonNull
  public final MSPTextView tvCartItemTitle;

  @NonNull
  public final MSPTextView tvCartQuantity;

  private ItemCartLayoutBinding(@NonNull LinearLayout rootView, @NonNull ImageButton ibAddCartItem,
      @NonNull ImageButton ibDeleteCartItem, @NonNull ImageButton ibRemoveCartItem,
      @NonNull ImageView ivCartItemImage, @NonNull LinearLayout llCartItemDetails,
      @NonNull LinearLayout llCartItemImage, @NonNull LinearLayout llUpdateCartQuantity,
      @NonNull MSPTextViewBold tvCartItemPrice, @NonNull MSPTextView tvCartItemTitle,
      @NonNull MSPTextView tvCartQuantity) {
    this.rootView = rootView;
    this.ibAddCartItem = ibAddCartItem;
    this.ibDeleteCartItem = ibDeleteCartItem;
    this.ibRemoveCartItem = ibRemoveCartItem;
    this.ivCartItemImage = ivCartItemImage;
    this.llCartItemDetails = llCartItemDetails;
    this.llCartItemImage = llCartItemImage;
    this.llUpdateCartQuantity = llUpdateCartQuantity;
    this.tvCartItemPrice = tvCartItemPrice;
    this.tvCartItemTitle = tvCartItemTitle;
    this.tvCartQuantity = tvCartQuantity;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCartLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCartLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_cart_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCartLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ib_add_cart_item;
      ImageButton ibAddCartItem = ViewBindings.findChildViewById(rootView, id);
      if (ibAddCartItem == null) {
        break missingId;
      }

      id = R.id.ib_delete_cart_item;
      ImageButton ibDeleteCartItem = ViewBindings.findChildViewById(rootView, id);
      if (ibDeleteCartItem == null) {
        break missingId;
      }

      id = R.id.ib_remove_cart_item;
      ImageButton ibRemoveCartItem = ViewBindings.findChildViewById(rootView, id);
      if (ibRemoveCartItem == null) {
        break missingId;
      }

      id = R.id.iv_cart_item_image;
      ImageView ivCartItemImage = ViewBindings.findChildViewById(rootView, id);
      if (ivCartItemImage == null) {
        break missingId;
      }

      id = R.id.ll_cart_item_details;
      LinearLayout llCartItemDetails = ViewBindings.findChildViewById(rootView, id);
      if (llCartItemDetails == null) {
        break missingId;
      }

      id = R.id.ll_cart_item_image;
      LinearLayout llCartItemImage = ViewBindings.findChildViewById(rootView, id);
      if (llCartItemImage == null) {
        break missingId;
      }

      id = R.id.ll_update_cart_quantity;
      LinearLayout llUpdateCartQuantity = ViewBindings.findChildViewById(rootView, id);
      if (llUpdateCartQuantity == null) {
        break missingId;
      }

      id = R.id.tv_cart_item_price;
      MSPTextViewBold tvCartItemPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvCartItemPrice == null) {
        break missingId;
      }

      id = R.id.tv_cart_item_title;
      MSPTextView tvCartItemTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvCartItemTitle == null) {
        break missingId;
      }

      id = R.id.tv_cart_quantity;
      MSPTextView tvCartQuantity = ViewBindings.findChildViewById(rootView, id);
      if (tvCartQuantity == null) {
        break missingId;
      }

      return new ItemCartLayoutBinding((LinearLayout) rootView, ibAddCartItem, ibDeleteCartItem,
          ibRemoveCartItem, ivCartItemImage, llCartItemDetails, llCartItemImage,
          llUpdateCartQuantity, tvCartItemPrice, tvCartItemTitle, tvCartQuantity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
