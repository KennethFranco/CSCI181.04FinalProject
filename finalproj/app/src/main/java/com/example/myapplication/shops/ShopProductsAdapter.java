package com.example.myapplication.shops;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.customers.CustomerShopsProducts;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class ShopProductsAdapter extends RealmRecyclerViewAdapter<Products, ShopProductsAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    CustomerShopsProducts activity;

    public ShopProductsAdapter(CustomerShopsProducts activity, @Nullable OrderedRealmCollection<Products> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView product_name;
        TextView product_price;
        TextView product_description;
        EditText product_quantity;
        ImageButton product_remove;
        Button product_addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_name = itemView.findViewById(R.id.shopProductName);
            product_price = itemView.findViewById(R.id.shopProductPrice);
            product_description = itemView.findViewById(R.id.shopProductDescription);

            // initialize the buttons in the layout
            product_quantity = itemView.findViewById(R.id.editTextNumber);
            product_remove = itemView.findViewById(R.id.removeItem);
            product_addToCart = itemView.findViewById(R.id.addItemToCart);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_shop_products, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

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
        holder.product_price.setText(u.getProduct_price());
        holder.product_description.setText(u.getProduct_description());

        holder.product_remove.setTag(u);
        holder.product_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.product_addToCart.setTag(u);
        holder.product_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {activity.addItem((Products) v.getTag());
            }
        });

        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
//        holder.delete.setTag(u);
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.delete((Products) view.getTag());
//            }
//        });
//
//        holder.edit.setTag(u);
//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                activity.edit((Products) view.getTag());
//            }
//        });
    }

}