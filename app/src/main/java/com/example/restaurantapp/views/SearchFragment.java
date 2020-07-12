package com.example.restaurantapp.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantapp.R;
import com.example.restaurantapp.adapters.SearchAdapter;
import com.example.restaurantapp.models.Search;
import com.example.restaurantapp.viewmodels.SearchViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private SearchAdapter adapter;

    private TextInputEditText keywordEditText;
    private Button searchButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SearchAdapter();

        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        viewModel.init();
        viewModel.getSearchLiveData().observe(this, new Observer<Search>() {
            @Override
            public void onChanged(Search search) {
                Log.d("TAG", "onChanged: "+(search != null));
                if (search != null) {
                    adapter.setResults(search.getRestaurants());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.searchKeyword);
        searchButton = view.findViewById(R.id.searchButton);


        viewModel.search("");

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();

        viewModel.search(keyword);
    }
}
