package com.example.restaurantapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.apis.ApiClient;
import com.example.restaurantapp.apis.ApiInterface;
import com.example.restaurantapp.models.Geocode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodeRepository {
    private ApiInterface apiInterface;
    private MutableLiveData<Geocode> geocodeMutableLiveData;
    private List<Geocode> geocode;

    public GeocodeRepository() {
        geocodeMutableLiveData = new MutableLiveData<>();
        geocode = new ArrayList<>();
        apiInterface = ApiClient.providesHttpAdapter().create(ApiInterface.class);
    }

    public void geocode(double lat, double lon, String apiKey) {
        Call<Geocode> listCall = apiInterface.geocode(lat, lon, apiKey);
        listCall.enqueue(new Callback<Geocode>() {
                    @Override
                    public void onResponse(Call<Geocode> call, Response<Geocode> response) {
                        if (response.isSuccessful()) {
                            geocodeMutableLiveData.setValue(response.body());
                            geocode.add(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Geocode> call, Throwable t) {
                        geocodeMutableLiveData.setValue(null);
                        geocode = null;
                    }
                });
    }

    public MutableLiveData<Geocode> getGeocodeMutableLiveData() {
        return geocodeMutableLiveData;
    }

    public List<Geocode> getGeocode() {
        return geocode;
    }
}
