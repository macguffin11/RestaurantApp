package com.example.restaurantapp.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.R;
import com.example.restaurantapp.adapters.RestaurantAdapter;
import com.example.restaurantapp.models.Geocode;
import com.example.restaurantapp.models.Search;
import com.example.restaurantapp.viewmodels.GeocodeViewModel;
import com.example.restaurantapp.viewmodels.SearchViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RestaurantFragment extends Fragment {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private SearchViewModel viewModel;
    private GeocodeViewModel geocodeViewModel;
    private RestaurantAdapter adapter;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextInputEditText keywordEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RestaurantAdapter();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        geocodeViewModel = ViewModelProviders.of(this).get(GeocodeViewModel.class);
        geocodeViewModel.init();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.init();
        viewModel.getSearchLiveData().observe(this, new Observer<Search>() {
            @Override
            public void onChanged(Search search) {
                Log.d("TAG", "onChanged: " + (search != null));
                if (search != null) {
                    adapter.setResults(search.getRestaurants());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.searchKeyword);

        keywordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        if (keywordEditText.getEditableText().toString().matches("")) {
            performSearch();
        }

        checkPermission();

        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();

        geocodeViewModel.getGeocodeLiveData().observe(this, new Observer<Geocode>() {
            @Override
            public void onChanged(Geocode geocode) {
                if (geocode != null) {
                    if (keyword.matches("")){
                        viewModel.search(
                                geocode.getLocation().getEntityId(),
                                geocode.getLocation().getEntityType(),
                                geocode.getLocation().getLatitude(),
                                geocode.getLocation().getLongitude());
                    }else{
                        viewModel.search(
                                keyword,
                                geocode.getLocation().getEntityId(),
                                geocode.getLocation().getLatitude(),
                                geocode.getLocation().getLongitude());
                    }
                }
            }
        });
    }

    private void checkPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            getCurrentLocation();
        } else {
            requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        }
    }

    private void requestPermissions(FragmentActivity activity, String[] strings, int requestCodeLocationPermission) {
        if (ActivityCompat.checkSelfPermission(getActivity(), strings[0]) == PackageManager.PERMISSION_GRANTED ) {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(),
                            Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    geocodeViewModel.geocode(
                            addresses.get(0).getLatitude(),
                            addresses.get(0).getLongitude());
                }
            }
        });
    }
}