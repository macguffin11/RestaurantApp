package com.example.restaurantapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.models.Search;
import com.example.restaurantapp.repositories.SearchRepository;
import com.example.restaurantapp.utils.Config;

public class SearchViewModel extends ViewModel {
    private SearchRepository searchRepository;
    private MutableLiveData<Search> searchMutableLiveData;

    public void init() {
        searchRepository = new SearchRepository();
        searchMutableLiveData = searchRepository.getSearchMutableLiveData();
    }

    public void search(String keyword) {
        searchRepository.search(keyword, Config.API_KEY);
    }

    public LiveData<Search> getSearchLiveData() {
        return searchMutableLiveData;
    }
}
