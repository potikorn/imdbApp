package com.example.potikorn.movielists.fetchapi;

import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApisInteractorImpl implements ApisInteractor{


    @Override
    public <T> void fetchApi(Observable<Response<T>> observable, OnConnectionResponse callback) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriberData<T>(callback));
    }

    private class SubscriberData<T> extends Subscriber<Response<T>> {
        private OnConnectionResponse callback;

        SubscriberData(OnConnectionResponse callback) {
            this.callback = callback;
        }

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            try {
                callback.onFailure(e);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        @Override
        public void onNext(Response<T> tResponse) {
            if (tResponse.code() == 401) {
                //TODO in case unauthorize from server
            } else if (tResponse.isSuccessful()) {
                callback.onSuccess(tResponse.body());
            } else {
                //TODO in case server response unexpected data
            }
        }
    }
}
