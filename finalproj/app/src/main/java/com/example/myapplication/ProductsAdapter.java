package com.example.myapplication;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class ProductsAdapter extends RealmRecyclerViewAdapter<Products, ProductsAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    ViewProducts activity;

    public ProductsAdapter(ViewProducts activity, @Nullable OrderedRealmCollection<Products> data, boolean autoUpdate) {
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
        TextView product_total_qty;
        TextView product_earnings;
        ImageButton delete;
        ImageButton edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_name = itemView.findViewById(R.id.display_productName);
            product_price = itemView.findViewById(R.id.display_productPrice);
            product_description = itemView.findViewById(R.id.display_productDescription);
            product_total_qty = itemView.findViewById(R.id.productTotalQuantitySold);
            product_earnings = itemView.findViewById(R.id.productTotalEarnings);

            // initialize the buttons in the layout
            delete = itemView.findViewById(R.id.deleteButton);
            edit = itemView.findViewById(R.id.editButton);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_products, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

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


        holder.product_price.setText(String.valueOf(u.getProduct_price()));
        holder.product_description.setText(u.getProduct_description());

        holder.product_total_qty.setText("Total Quantity Sold: "+String.valueOf(u.getTotalQty()));
        holder.product_earnings.setText("Total Earned: PHP "+String.valueOf(u.getTotalPrice()));

        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
        holder.delete.setTag(u);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete((Products) view.getTag());
            }
        });

        holder.edit.setTag(u);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.edit((Products) view.getTag());
            }
        });
    }

}