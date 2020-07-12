package com.example.restaurantapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.restaurantapp.apis.ApiClient;
import com.example.restaurantapp.apis.ApiInterface;
import com.example.restaurantapp.models.Search;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private ApiInterface apiInterface;
    private MutableLiveData<Search> searchMutableLiveData;

    public SearchRepository() {
        searchMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.providesHttpAdapter().create(ApiInterface.class);
    }

    public void search(String keyword, String apiKey) {
        Call<Search> listCall = apiInterface.search(keyword, apiKey);
        Log.d("TAG", "search: "+(listCall == null));
        listCall.enqueue(new Callback<Search>() {
                    @Override
                    public void onResponse(Call<Search> call, Response<Search> response) {
                        if (response.isSuccessful()) {
                            searchMutableLiveData.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Search> call, Throwable t) {
                        searchMutableLiveData.setValue(null);
                        Log.d("Call Failure", "onFailure: "+t.getMessage());
                    }
                });
    }

    public MutableLiveData<Search> getSearchMutableLiveData() {
        return searchMutableLiveData;
    }
}
