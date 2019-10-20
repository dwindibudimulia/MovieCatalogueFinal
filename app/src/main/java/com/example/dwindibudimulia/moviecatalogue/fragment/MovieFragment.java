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
import com.example.dwindibudimulia.moviecatalogue.activity.ListMovieFavoriteActivity;
import com.example.dwindibudimulia.moviecatalogue.activity.MovieDetailActivity;
import com.example.dwindibudimulia.moviecatalogue.adapter.MovieAdapter;
import com.example.dwindibudimulia.moviecatalogue.itemClick.ItemClickSupport;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;
import com.example.dwindibudimulia.moviecatalogue.viewmodel.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Objects;

public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private static MovieViewModel movieViewModel;

    private AVLoadingIndicatorView progressBar;

    public static MovieViewModel getMovieViewModel() {
        return movieViewModel;
    }

    public static Fragment newInstance() {
        return new MovieFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.progress_movie);
        showLoading(true);
        setHasOptionsMenu(true);

        showRecyclerList(view);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovie().observe(this, getMovie);
        movieViewModel.setMovie();


        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ListMovieFavoriteActivity.class);
            startActivity(intent);
        });
        showLoading(true);
    }

    private final Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setMovies(movies);
                showLoading(false);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) ->
                        showSelectedMovie(movies.get(position)));
            }
        }
    };

    private void showRecyclerList(View view) {
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, view.isInLayout()));
        recyclerView.setAdapter(movieAdapter);

    }

    private void showSelectedMovie(Movie movie) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.show();
        } else {
            progressBar.hide();
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
                    movieViewModel.setMovieSearch(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    movieViewModel.setEmptyMovie();
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
                movieViewModel.setMovie();
                return true;
            }
        });
    }
}
