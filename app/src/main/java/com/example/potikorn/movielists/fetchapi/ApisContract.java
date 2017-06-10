package com.example.potikorn.movielists.fetchapi;

import retrofit2.Response;
import rx.Observable;

public interface ApisContract {

    interface ApisView {

        void showLoading();

        void hideLoading();

        <T> void onLoadSuccess(T newDao);

        void onUnSuccess(String message);

        void onFailure(Throwable t);

    }

    interface ApisPresenter {

        <T> void loadData(Observable<Response<T>> observable);

        void onDestroy();

    }

}
