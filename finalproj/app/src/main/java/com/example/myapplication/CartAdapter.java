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

import org.w3c.dom.Text;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class CartAdapter extends RealmRecyclerViewAdapter<Cart, CartAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    ViewCart activity;

    public CartAdapter(ViewCart activity, @Nullable OrderedRealmCollection<Cart> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView product_name;
        TextView shop_name;
        TextView total_price;
        TextView quantity;
        ImageButton add;
        ImageButton minus;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_name = itemView.findViewById(R.id.viewCartProductName);
            shop_name   = itemView.findViewById(R.id.viewCartShopName);
            total_price = itemView.findViewById(R.id.viewCartTotalPrice);
            quantity = itemView.findViewById(R.id.viewCartQuantity);
            add = itemView.findViewById(R.id.viewCartAddQtyButton);
            minus = itemView.findViewById(R.id.viewCartMinusQtyButton);
            delete = itemView.findViewById(R.id.viewCartDeleteButton);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_cart, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        Cart u = getItem(position);

        holder.product_name.setText(u.getProduct_name());
        holder.shop_name.setText(u.getShop_name());
        holder.total_price.setText(String.valueOf(u.getTotal_price()));
        holder.quantity.setText(String.valueOf(u.getQuantity()));
//
//        // NOTE: MUST BE A STRING NOT INTs, etc.
//        // String.valueOf() converts most types to a string
//        // holder.age.setText(String.valueOf(u.getAge()));
//
//        // do any other initializations here as well,  e.g. Button listeners
        holder.add.setTag(u);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.add((Cart) view.getTag());
            }
        });

        holder.minus.setTag(u);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.minus((Cart) view.getTag());
            }
        });

        holder.delete.setTag(u);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete((Cart) view.getTag());
            }
        });
    }

}