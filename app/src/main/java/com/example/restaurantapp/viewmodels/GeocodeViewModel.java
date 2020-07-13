package com.example.restaurantapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.models.Geocode;
import com.example.restaurantapp.repositories.GeocodeRepository;
import com.example.restaurantapp.utils.Config;

public class GeocodeViewModel extends ViewModel {
    private GeocodeRepository geocodeRepository;
    private MutableLiveData<Geocode> geocodeMutableLiveData;

    public void init() {
        geocodeRepository = new GeocodeRepository();
        geocodeMutableLiveData = geocodeRepository.getGeocodeMutableLiveData();
    }

    public void geocode(double lat, double lon) {
        geocodeRepository.geocode(lat, lon, Config.API_KEY);
    }

    public LiveData<Geocode> getGeocodeLiveData() {
        return geocodeMutableLiveData;
    }
}
