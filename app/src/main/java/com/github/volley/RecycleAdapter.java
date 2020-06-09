package com.github.volley;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<User> contactList;
    private List<User> contactListFiltered;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userStats;
        public ImageView userPicture;



        public MyViewHolder(View view) {
            super(view);
            userPicture = view.findViewById(R.id.userPicture);
            userName = view.findViewById(R.id.userName);
            userStats = view.findViewById(R.id.userStats);
        }
    }


    public RecycleAdapter(Context context, List<User> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.contactListFiltered = contactList;
    }

    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);

        return new RecycleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleAdapter.MyViewHolder holder, final int position) {
        final User contact = contactListFiltered.get(position);

        Glide.with(context)
                .load(contact.getPicture())
                .into(holder.userPicture);

        holder.userName.setText(contact.getName());
        holder.userStats.setText(contact.getStats());

    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(User contact);
    }
}
