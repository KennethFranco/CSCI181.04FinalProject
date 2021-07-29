package com.example.myapplication;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

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
        ImageView product_image;
        Button add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_name = itemView.findViewById(R.id.shopProductName);
            product_price = itemView.findViewById(R.id.shopProductPrice);
            product_image = itemView.findViewById(R.id.specificProductImage);
            add = itemView.findViewById(R.id.shopAdd);

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

        File getImageDir = activity.getExternalCacheDir();

        File file = new File(getImageDir, u.getImagePath());

        Picasso.get()
                .load(file)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.product_image);

        // copy all the values needed to the appropriate views
        holder.product_name.setText(u.getProduct_name());

        String price = u.getProduct_price();
        holder.product_price.setText("PHP "+price);

        holder.add.setTag(u);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.add((Products) view.getTag());
            }
        });
    }

}