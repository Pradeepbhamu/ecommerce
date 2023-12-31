// Generated by view binder compiler. Do not edit!
package com.onlineShop.shopit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.onlineShop.shopit.R;
import com.onlineShop.shopit.utils.MSPTextView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityAddressListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView rvAddressList;

  @NonNull
  public final Toolbar toolbarAddressListActivity;

  @NonNull
  public final MSPTextView tvAddAddress;

  @NonNull
  public final MSPTextView tvNoAddressFound;

  @NonNull
  public final TextView tvTitle;

  private ActivityAddressListBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView rvAddressList, @NonNull Toolbar toolbarAddressListActivity,
      @NonNull MSPTextView tvAddAddress, @NonNull MSPTextView tvNoAddressFound,
      @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.rvAddressList = rvAddressList;
    this.toolbarAddressListActivity = toolbarAddressListActivity;
    this.tvAddAddress = tvAddAddress;
    this.tvNoAddressFound = tvNoAddressFound;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityAddressListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityAddressListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_address_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityAddressListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.rv_address_list;
      RecyclerView rvAddressList = ViewBindings.findChildViewById(rootView, id);
      if (rvAddressList == null) {
        break missingId;
      }

      id = R.id.toolbar_address_list_activity;
      Toolbar toolbarAddressListActivity = ViewBindings.findChildViewById(rootView, id);
      if (toolbarAddressListActivity == null) {
        break missingId;
      }

      id = R.id.tv_add_address;
      MSPTextView tvAddAddress = ViewBindings.findChildViewById(rootView, id);
      if (tvAddAddress == null) {
        break missingId;
      }

      id = R.id.tv_no_address_found;
      MSPTextView tvNoAddressFound = ViewBindings.findChildViewById(rootView, id);
      if (tvNoAddressFound == null) {
        break missingId;
      }

      id = R.id.tv_title;
      TextView tvTitle = ViewBindings.findChildViewById(rootView, id);
      if (tvTitle == null) {
        break missingId;
      }

      return new ActivityAddressListBinding((ConstraintLayout) rootView, rvAddressList,
          toolbarAddressListActivity, tvAddAddress, tvNoAddressFound, tvTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
