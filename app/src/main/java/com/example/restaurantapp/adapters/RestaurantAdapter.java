package com.example.restaurantapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.R;
import com.example.restaurantapp.models.Restaurants;
import com.example.restaurantapp.views.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.SearchHolder> {
    private List<Restaurants> results = new ArrayList<>();
    private Context mContext;

    Bundle bundle = null;
    Intent intent = null;

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

        bundle=new Bundle();

        Double rating = restaurants.getRestaurant().getUserRating().getAggregateRating();

        bundle.putString("alamat", restaurants.getRestaurant().getLocation().getAddress());
        bundle.putDouble("latitude",restaurants.getRestaurant().getLocation().getLatitude());
        bundle.putDouble("longitude",restaurants.getRestaurant().getLocation().getLongitude());
        bundle.putDouble("rating",rating);
        bundle.putString("ratingcolor","#"+restaurants.getRestaurant().getUserRating().getRatingColor());
        bundle.putString("nama",restaurants.getRestaurant().getName());
        bundle.putString("thumbnail",restaurants.getRestaurant().getFeaturedImage());

        holder.nameTextView.setText(restaurants.getRestaurant().getName());
        holder.ratingBar.setRating((float) restaurants.getRestaurant().getUserRating().getAggregateRating());
        holder.aggregateRatingTextView.setText(
                Double.toString(restaurants.getRestaurant().getUserRating().getAggregateRating())
        );
        String ratingColor = "#"+restaurants.getRestaurant().getUserRating().getRatingColor();
        holder.aggregateRatingTextView.setTextColor(Color.parseColor(ratingColor));
        holder.statusTextView.setText(
                (restaurants.getRestaurant().getIsDeliveringNow() == 1)? "Open Delivering" : "Close Delivering"
        );
        holder.statusTextView.setTextColor(
                (restaurants.getRestaurant().getIsDeliveringNow() == 1)?
                        Color.parseColor("#42b549") : Color.parseColor("#ea2626")
                );

        String averagePrice = restaurants.getRestaurant().getCurrency()
                +Integer.toString(restaurants.getRestaurant().getAverageCostForTwo());

        bundle.putString("harga",averagePrice+" untuk 2 orang");

        holder.averageCostForTwoTextView.setText(averagePrice);

        Glide.with(holder.itemView)
                .load(restaurants.getRestaurant().getFeaturedImage())
                .placeholder(R.drawable.ic_image_not_found)
                .into(holder.smallThumbnailImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Restaurants> results, Context context) {
        this.results = results;
        this.mContext = context;
        notifyDataSetChanged();
    }

    class SearchHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private RatingBar ratingBar;
        private TextView aggregateRatingTextView;
        private TextView statusTextView;
        private TextView averageCostForTwoTextView;
        private ImageView smallThumbnailImageView;
        private CardView cardView;
        private final Context context;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            nameTextView = itemView.findViewById(R.id.name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            aggregateRatingTextView = itemView.findViewById(R.id.aggregateRating);
            statusTextView = itemView.findViewById(R.id.status);
            averageCostForTwoTextView = itemView.findViewById(R.id.averageCostForTwo);
            smallThumbnailImageView = itemView.findViewById(R.id.smallThumbnail);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
