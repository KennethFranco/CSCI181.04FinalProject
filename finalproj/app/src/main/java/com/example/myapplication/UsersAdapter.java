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
public class UsersAdapter extends RealmRecyclerViewAdapter<Users, UsersAdapter.ViewHolder> {

    // IMPORTANT
    // THE CONTAINING ACTIVITY NEEDS TO BE PASSED SO YOU CAN GET THE LayoutInflator(see below)
    AdminViewCustomers activity;

    public UsersAdapter(AdminViewCustomers activity, @Nullable OrderedRealmCollection<Users> data, boolean autoUpdate) {
        super(data, autoUpdate);

        // THIS IS TYPICALLY THE ACTIVITY YOUR RECYCLERVIEW IS IN
        this.activity = activity;
    }

    // THIS DEFINES WHAT VIEWS YOU ARE FILLING IN
    public class ViewHolder extends RecyclerView.ViewHolder {

        // have a field for each one
        TextView username;
        TextView password;
        ImageButton clear_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize them from the itemView using standard style
            username = itemView.findViewById(R.id.admin_CustomerUname);
            password = itemView.findViewById(R.id.admin_CustomerPword);
            clear_user = itemView.findViewById(R.id.admin_ClearUser);
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
        Users u = getItem(position);
        System.out.println(u);


        // copy all the values needed to the appropriate views
        holder.username.setText("Username: " + u.getUsername());
        holder.password.setText("Password: + " + u.getPassword());

        // NOTE: MUST BE A STRING NOT INTs, etc.
        // String.valueOf() converts most types to a string
        // holder.age.setText(String.valueOf(u.getAge()));

        // do any other initializations here as well,  e.g. Button listeners
        holder.clear_user.setTag(u);
        holder.clear_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.ClearUser((Users) view.getTag());
            }
        });



    }

}
