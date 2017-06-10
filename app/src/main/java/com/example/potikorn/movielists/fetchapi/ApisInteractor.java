package com.example.potikorn.movielists.fetchapi;

import retrofit2.Response;
import rx.Observable;

public interface ApisInteractor {

    interface OnConnectionResponse {

        <T> void onSuccess(T dao);

        void onUnSuccess(String message);

        void onFailure(Throwable t);
    }

    <T> void fetchApi(Observable<Response<T>> observable, OnConnectionResponse callback);


}
