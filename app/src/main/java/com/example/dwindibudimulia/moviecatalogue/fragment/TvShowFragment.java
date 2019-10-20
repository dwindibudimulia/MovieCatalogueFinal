package com.example.dwindibudimulia.moviecatalogue.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.activity.ListTvShowFavoriteActivity;
import com.example.dwindibudimulia.moviecatalogue.activity.TvShowDetailActivity;
import com.example.dwindibudimulia.moviecatalogue.adapter.TvShowAdapter;
import com.example.dwindibudimulia.moviecatalogue.itemClick.ItemClickSupport;
import com.example.dwindibudimulia.moviecatalogue.model.TvShow;
import com.example.dwindibudimulia.moviecatalogue.viewmodel.TvShowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Objects;

public class TvShowFragment extends Fragment {
    private RecyclerView rvTvShow;
    private TvShowAdapter tvShowAdapter;
    private AVLoadingIndicatorView progressBarTv;
    private TvShowViewModel tvShowViewModel;

    public static Fragment newInstance() {
        return new TvShowFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShow = view.findViewById(R.id.rv_tv_show);
        progressBarTv = view.findViewById(R.id.progress_tv);
        showLoading(true);
        setHasOptionsMenu(true);

        showRecyclerTvShow(view);

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.gettvShow().observe(this, getTvShow);
        tvShowViewModel.setTvShow();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListTvShowFavoriteActivity.class);
            startActivity(intent);
        });
        showLoading(true);
    }

    private final Observer<ArrayList<TvShow>> getTvShow = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setTvShow(tvShows);
                showLoading(false);
                ItemClickSupport.addTo(rvTvShow).setOnItemClickListener((recyclerView, position, v) ->
                        showSelectedTvShow(tvShows.get(position)));
            }
        }
    };

    private void showRecyclerTvShow(View view) {
        tvShowAdapter = new TvShowAdapter();
        tvShowAdapter.notifyDataSetChanged();
        rvTvShow.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        rvTvShow.setAdapter(tvShowAdapter);

    }

    private void showSelectedTvShow(TvShow tvShow) {
        Intent intent = new Intent(getActivity(), TvShowDetailActivity.class);
        intent.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW, tvShow);
        startActivity(intent);
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBarTv.show();
        } else {
            progressBarTv.hide();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem search = menu.findItem(R.id.search_bar);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) (menu.findItem(R.id.search_bar)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    showLoading(true);
                    tvShowViewModel.setTvShowSearch(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    tvShowViewModel.setEmptyTv();
                    return true;
                }
            });
        }
        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                showLoading(true);
                tvShowViewModel.setTvShow();
                return true;
            }
        });
    }
}
