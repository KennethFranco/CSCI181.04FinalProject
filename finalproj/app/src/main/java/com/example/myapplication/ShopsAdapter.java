package com.example.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

// the parameterization <type of the RealmObject, ViewHolder type)
public class ShopsAdapter extends RealmRecyclerViewAdapter<Shops, ShopsAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    CustomerShops activity;

    public ShopsAdapter(CustomerShops activity, @Nullable OrderedRealmCollection<Shops> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView a;
        TextView b;
        ImageButton c;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            a = itemView.findViewById(R.id.rowShopName);
            b = itemView.findViewById(R.id.rowShopDescription);
            c = itemView.findViewById(R.id.rowShopViewButton);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // create the raw view for this ViewHolder
        View v = activity.getLayoutInflater().inflate(R.layout.row_layout_shops, parent, false);  // VERY IMPORTANT TO USE THIS STYLE

        // assign view to the viewholder
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        Shops u = getItem(position);
        System.out.println(u);


        // copy all the values needed to the appropriate views
        holder.a.setText(u.getShopName());
        holder.b.setText(u.getShopDescription());

        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
        holder.c.setTag(u);
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.c((Shops) view.getTag());
            }
        });



    }

}
