package com.example.potikorn.movielists.fetchapi;

import retrofit2.Response;
import rx.Observable;

public class ApisPresenterImpl implements ApisContract.ApisPresenter, ApisInteractor.OnConnectionResponse {

    private ApisContract.ApisView view;
    private ApisInteractor interactor;

    public ApisPresenterImpl(ApisContract.ApisView view) {
        this.view = view;
        interactor = new ApisInteractorImpl();
    }

    @Override
    public <T> void loadData(Observable<Response<T>> observable) {
        view.showLoading();
        interactor.fetchApi(observable, this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public <T> void onSuccess(T dao) {
        if (view != null) {
            view.hideLoading();
            view.onLoadSuccess(dao);
        }
    }

    @Override
    public void onUnSuccess(String message) {
        if (view != null) {
            view.hideLoading();
            view.onUnSuccess(message);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (view != null) {
            view.hideLoading();
            view.onFailure(t);
        }
    }
}
