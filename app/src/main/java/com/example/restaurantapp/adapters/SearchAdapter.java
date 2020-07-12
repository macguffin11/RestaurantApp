package com.example.restaurantapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.restaurantapp.R;
import com.example.restaurantapp.models.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private List<Restaurants> results = new ArrayList<>();

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items, parent, false);

        return new SearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        Restaurants restaurants = (Restaurants) results.toArray()[position];

        holder.nameTextView.setText(restaurants.getRestaurant().getName());
        holder.phoneNumberTextView.setText(restaurants.getRestaurant().getPhoneNumbers());
        holder.priceRangeTextView.setText(Integer.toString(restaurants.getRestaurant().getPriceRange()));

        if (restaurants.getRestaurant().getThumb() != null) {
            Glide.with(holder.itemView)
                    .load(restaurants.getRestaurant().getThumb())
                    .into(holder.smallThumbnailImageView);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Restaurants> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class SearchHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneNumberTextView;
        private TextView priceRangeTextView;
        private ImageView smallThumbnailImageView;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            phoneNumberTextView = itemView.findViewById(R.id.phoneNumber);
            priceRangeTextView = itemView.findViewById(R.id.priceRange);
            smallThumbnailImageView = itemView.findViewById(R.id.smallThumbnail);
        }
    }
}
