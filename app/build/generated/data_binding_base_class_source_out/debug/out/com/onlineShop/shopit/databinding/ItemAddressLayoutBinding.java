// Generated by view binder compiler. Do not edit!
package com.onlineShop.shopit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ItemAddressLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final MSPTextView tvAddressDetails;

  @NonNull
  public final MSPTextViewBold tvAddressFullName;

  @NonNull
  public final MSPTextView tvAddressMobileNumber;

  @NonNull
  public final MSPTextView tvAddressType;

  private ItemAddressLayoutBinding(@NonNull LinearLayout rootView,
      @NonNull MSPTextView tvAddressDetails, @NonNull MSPTextViewBold tvAddressFullName,
      @NonNull MSPTextView tvAddressMobileNumber, @NonNull MSPTextView tvAddressType) {
    this.rootView = rootView;
    this.tvAddressDetails = tvAddressDetails;
    this.tvAddressFullName = tvAddressFullName;
    this.tvAddressMobileNumber = tvAddressMobileNumber;
    this.tvAddressType = tvAddressType;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemAddressLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemAddressLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_address_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemAddressLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tv_address_details;
      MSPTextView tvAddressDetails = ViewBindings.findChildViewById(rootView, id);
      if (tvAddressDetails == null) {
        break missingId;
      }

      id = R.id.tv_address_full_name;
      MSPTextViewBold tvAddressFullName = ViewBindings.findChildViewById(rootView, id);
      if (tvAddressFullName == null) {
        break missingId;
      }

      id = R.id.tv_address_mobile_number;
      MSPTextView tvAddressMobileNumber = ViewBindings.findChildViewById(rootView, id);
      if (tvAddressMobileNumber == null) {
        break missingId;
      }

      id = R.id.tv_address_type;
      MSPTextView tvAddressType = ViewBindings.findChildViewById(rootView, id);
      if (tvAddressType == null) {
        break missingId;
      }

      return new ItemAddressLayoutBinding((LinearLayout) rootView, tvAddressDetails,
          tvAddressFullName, tvAddressMobileNumber, tvAddressType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
