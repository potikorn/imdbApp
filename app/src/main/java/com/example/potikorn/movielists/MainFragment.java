package com.example.potikorn.movielists;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.potikorn.movielists.dao.Film;
import com.example.potikorn.movielists.dao.FilmModel;
import com.example.potikorn.movielists.fetchapi.ApisContract;
import com.example.potikorn.movielists.fetchapi.ApisPresenterImpl;
import com.example.potikorn.movielists.httpmanager.ApisService;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements ApisContract.ApisView {

    private ApisContract.ApisPresenter presenter;
    private List<FilmModel> film = new ArrayList<>();
    private RecyclerView recyclerView;
    private FilmAdapter filmAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Film films;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        initInstance(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (films == null)
            presenter.loadData(ApisService.getMoviesApi().getMovieList("Pirates of the Caribbean"));
        else
            initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("data", Parcels.wrap(films));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            films = Parcels.unwrap(savedInstanceState.getParcelable("data"));
        } else {
            presenter.loadData(ApisService.getMoviesApi().getMovieList("Pirates of the Caribbean"));
        }
    }

    private void initInstance(View view) {
        presenter = new ApisPresenterImpl(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_movie_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(ApisService.getMoviesApi().getMovieList("Pirates of the Caribbean"));
            }
        });
    }

    private void initData() {

        if (film != null)
            film.clear();

        for (int i = 0; i < films.getMovieDetails().size(); i++) {
            FilmModel movies = films.getMovieDetails().get(i);
            film.add(new FilmModel(movies.getTitle(), movies.getVoteAverage(), movies.getPosterPath(), movies.getOverview(), movies.getReleaseDate()));
        }

        for (FilmModel titleName : film) {
            Log.d("getTitlaName", titleName.getTitle());
        }

        setDataToAdapter(film);
    }

    private void setDataToAdapter(List<FilmModel> filmModel) {
        filmAdapter = new FilmAdapter(filmModel, new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClick(FilmModel film) {
                //TODO presenter to click
//                presenter.onFilmItemClicked(film);
            }
        }, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(filmAdapter);

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public <T> void onLoadSuccess(T newDao) {
        films = (Film) newDao;
        Log.d("Ok succuess", "OK onload");
        initData();
    }

    @Override
    public void onUnSuccess(String message) {
        Log.d("Ok succuess", "OK onUnSuccess");
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("Ok succuess", "OK onFailure");
    }
}
