package com.example.restaurantapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.restaurantapp.models.Geocode;
import com.example.restaurantapp.models.Restaurants;
import com.example.restaurantapp.models.Search;
import com.example.restaurantapp.repositories.GeocodeRepository;
import com.example.restaurantapp.repositories.SearchRepository;
import com.example.restaurantapp.utils.Config;
import com.example.restaurantapp.views.RestaurantFragment;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private SearchRepository searchRepository;
    private MutableLiveData<Search> searchMutableLiveData;

    public void init() {
        searchRepository = new SearchRepository();
        searchMutableLiveData = searchRepository.getSearchMutableLiveData();
    }

    public void search(int entityId, String entityType, double lat, double lon) {
        searchRepository.search(entityId, entityType, lat, lon, Config.API_KEY);
    }

    public void search(String q, int entityId, double lat, double lon) {
        searchRepository.search(q, entityId, lat, lon, Config.API_KEY);
    }

    public LiveData<Search> getSearchLiveData() {
        return searchMutableLiveData;
    }
}