// Generated by view binder compiler. Do not edit!
package com.onlineShop.shopit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.onlineShop.shopit.R;
import com.onlineShop.shopit.utils.MSPButton;
import com.onlineShop.shopit.utils.MSPTextView;
import com.onlineShop.shopit.utils.MSPTextViewBold;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProductDetailsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MSPButton btnAddToCart;

  @NonNull
  public final MSPButton btnGoToCart;

  @NonNull
  public final ImageView ivProductDetailImage;

  @NonNull
  public final LinearLayout llProductDetailsQuantity;

  @NonNull
  public final LinearLayout llProductDetailsTitlePrice;

  @NonNull
  public final Toolbar toolbarProductDetailsActivity;

  @NonNull
  public final MSPTextView tvProductDetailsAvailableQuantity;

  @NonNull
  public final MSPTextView tvProductDetailsDescription;

  @NonNull
  public final MSPTextViewBold tvProductDetailsLabel;

  @NonNull
  public final MSPTextView tvProductDetailsPrice;

  @NonNull
  public final MSPTextViewBold tvProductDetailsQuantity;

  @NonNull
  public final MSPTextViewBold tvProductDetailsTitle;

  @NonNull
  public final TextView tvTitle;

  private ActivityProductDetailsBinding(@NonNull ConstraintLayout rootView,
      @NonNull MSPButton btnAddToCart, @NonNull MSPButton btnGoToCart,
      @NonNull ImageView ivProductDetailImage, @NonNull LinearLayout llProductDetailsQuantity,
      @NonNull LinearLayout llProductDetailsTitlePrice,
      @NonNull Toolbar toolbarProductDetailsActivity,
      @NonNull MSPTextView tvProductDetailsAvailableQuantity,
      @NonNull MSPTextView tvProductDetailsDescription,
      @NonNull MSPTextViewBold tvProductDetailsLabel, @NonNull MSPTextView tvProductDetailsPrice,
      @NonNull MSPTextViewBold tvProductDetailsQuantity,
      @NonNull MSPTextViewBold tvProductDetailsTitle, @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.btnAddToCart = btnAddToCart;
    this.btnGoToCart = btnGoToCart;
    this.ivProductDetailImage = ivProductDetailImage;
    this.llProductDetailsQuantity = llProductDetailsQuantity;
    this.llProductDetailsTitlePrice = llProductDetailsTitlePrice;
    this.toolbarProductDetailsActivity = toolbarProductDetailsActivity;
    this.tvProductDetailsAvailableQuantity = tvProductDetailsAvailableQuantity;
    this.tvProductDetailsDescription = tvProductDetailsDescription;
    this.tvProductDetailsLabel = tvProductDetailsLabel;
    this.tvProductDetailsPrice = tvProductDetailsPrice;
    this.tvProductDetailsQuantity = tvProductDetailsQuantity;
    this.tvProductDetailsTitle = tvProductDetailsTitle;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProductDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProductDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_product_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProductDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_add_to_cart;
      MSPButton btnAddToCart = ViewBindings.findChildViewById(rootView, id);
      if (btnAddToCart == null) {
        break missingId;
      }

      id = R.id.btn_go_to_cart;
      MSPButton btnGoToCart = ViewBindings.findChildViewById(rootView, id);
      if (btnGoToCart == null) {
        break missingId;
      }

      id = R.id.iv_product_detail_image;
      ImageView ivProductDetailImage = ViewBindings.findChildViewById(rootView, id);
      if (ivProductDetailImage == null) {
        break missingId;
      }

      id = R.id.ll_product_details_quantity;
      LinearLayout llProductDetailsQuantity = ViewBindings.findChildViewById(rootView, id);
      if (llProductDetailsQuantity == null) {
        break missingId;
      }

      id = R.id.ll_product_details_title_price;
      LinearLayout llProductDetailsTitlePrice = ViewBindings.findChildViewById(rootView, id);
      if (llProductDetailsTitlePrice == null) {
        break missingId;
      }

      id = R.id.toolbar_product_details_activity;
      Toolbar toolbarProductDetailsActivity = ViewBindings.findChildViewById(rootView, id);
      if (toolbarProductDetailsActivity == null) {
        break missingId;
      }

      id = R.id.tv_product_details_available_quantity;
      MSPTextView tvProductDetailsAvailableQuantity = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsAvailableQuantity == null) {
        break missingId;
      }

      id = R.id.tv_product_details_description;
      MSPTextView tvProductDetailsDescription = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsDescription == null) {
        break missingId;
      }

      id = R.id.tv_product_details_label;
      MSPTextViewBold tvProductDetailsLabel = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsLabel == null) {
        break missingId;
      }

      id = R.id.tv_product_details_price;
      MSPTextView tvProductDetailsPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsPrice == null) {
        break missingId;
      }

      id = R.id.tv_product_details_quantity;
      MSPTextViewBold tvProductDetailsQuantity = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsQuantity == null) {
        break missingId;
      }

      id = R.id.tv_product_details_title;
      MSPTextViewBold tvProductDetailsTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDetailsTitle == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityProductDetailsBinding((ConstraintLayout) rootView, btnAddToCart,
          btnGoToCart, ivProductDetailImage, llProductDetailsQuantity, llProductDetailsTitlePrice,
          toolbarProductDetailsActivity, tvProductDetailsAvailableQuantity,
          tvProductDetailsDescription, tvProductDetailsLabel, tvProductDetailsPrice,
          tvProductDetailsQuantity, tvProductDetailsTitle, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
