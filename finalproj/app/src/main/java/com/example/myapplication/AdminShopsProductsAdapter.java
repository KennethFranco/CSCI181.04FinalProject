package com.example.myapplication;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class AdminShopsProductsAdapter extends RealmRecyclerViewAdapter<Products, AdminShopsProductsAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    AdminShopsProducts activity;

    public AdminShopsProductsAdapter(AdminShopsProducts activity, @Nullable OrderedRealmCollection<Products> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView product_name;
        TextView product_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_name = itemView.findViewById(R.id.admin_shopProductName);
            product_price = itemView.findViewById(R.id.admin_shopProductPrice);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_admin_shop_products, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        Products u = getItem(position);


        // copy all the values needed to the appropriate views
        holder.product_name.setText(u.getProduct_name());

        String price = u.getProduct_price();
        holder.product_price.setText("PHP "+price);


    }

}