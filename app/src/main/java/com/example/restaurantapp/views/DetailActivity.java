package com.example.restaurantapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.restaurantapp.BuildConfig;
import com.example.restaurantapp.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView foto;
    TextView alamat,nama;
    TextView rating;
    TextView harga;
    MapView map;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        requestPermissionsIfNecessary(new String[] {
                // if you need to show the current location, uncomment the line below
                // Manifest.permission.ACCESS_FINE_LOCATION,
                // WRITE_EXTERNAL_STORAGE is required in order to show the map
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

        if ((getIntent().getData() != null) && ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0)) {
            //Handle the url passed through the intent
        } else {
            //proceed as normal
        }

        //Map
        final Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map=findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = map.getController();
        //Other than Map
        foto=findViewById(R.id.fotoRestoraninRestaurantAct);
        alamat=findViewById(R.id.restoranDescinRestaurantAct);
        nama=findViewById(R.id.restoranNameinRestaurantAct);
        rating=findViewById(R.id.restoranStarinRestaurantAct);
        harga=findViewById(R.id.restoranIsDeliveryinRestaurantAct);
        ratingBar=findViewById(R.id.ratingBar);

        Bundle bundle = intent.getExtras();
        Glide.with(this)
                .load(intent.getStringExtra("thumbnail"))
                .placeholder(R.drawable.ic_image_not_found)
                .into(foto);
        harga.setText(intent.getStringExtra("harga"));
        nama.setText(intent.getStringExtra("nama"));
        getSupportActionBar().setTitle(intent.getStringExtra("nama"));
        ratingBar.setRating((float) bundle.getDouble("rating"));
        rating.setText(Double.toString(bundle.getDouble("rating")));
        rating.setTextColor(Color.parseColor(intent.getStringExtra("ratingcolor")));
        alamat.setText(intent.getStringExtra("alamat"));

        //init Map Latitude and Point
        double Latitude= bundle.getDouble("latitude");
        double Longitude= bundle.getDouble("longitude");

        ArrayList<OverlayItem> item = new ArrayList<OverlayItem>();
        item.add(new OverlayItem("Location", "Restaurant pinpoint",new GeoPoint(Latitude,Longitude)));
        GeoPoint startPoint = new GeoPoint(Latitude,Longitude);
        Marker startMarker = new Marker(map);

        startMarker.setPosition(startPoint);
        startMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(startMarker);

        mapController.setZoom(16);
        mapController.setCenter(startPoint);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    1);
        }
    }

    public void addMarkinMap(){

    }
}