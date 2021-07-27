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
public class OrdersAdapter extends RealmRecyclerViewAdapter<Orders, OrdersAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    CustomerOrders activity;

    public OrdersAdapter(CustomerOrders activity, @Nullable OrderedRealmCollection<Orders> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView product_names;
        TextView order_uuid;
        TextView products_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            product_names = itemView.findViewById(R.id.customerOrdersOrders);
            order_uuid   = itemView.findViewById(R.id.customerOrdersUUID);
            products_price = itemView.findViewById(R.id.customerOrdersTotalPrice);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_orders, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders u = getItem(position);


        // copy all the values needed to the appropriate views
        holder.product_names.setText(u.getOrder_name());
        holder.order_uuid.setText("Order #"+u.getUuid());
        holder.products_price.setText("PHP "+u.getOrder_price().toString());
    }
}
