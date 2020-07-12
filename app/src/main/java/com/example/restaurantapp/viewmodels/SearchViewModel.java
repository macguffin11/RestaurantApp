package com.example.restaurantapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.models.Search;
import com.example.restaurantapp.repositories.SearchRepository;

public class SearchViewModel extends ViewModel {
    private static final String API_KEY = "77778ae6116592fd3e767445ebad15ab";
    private SearchRepository searchRepository;
    private MutableLiveData<Search> searchMutableLiveData;

    public void init() {
        searchRepository = new SearchRepository();
        searchMutableLiveData = searchRepository.getSearchMutableLiveData();
    }

    public void search(String keyword) {
        searchRepository.search(keyword, API_KEY);
    }

    public LiveData<Search> getSearchLiveData() {
        return searchMutableLiveData;
    }
}
