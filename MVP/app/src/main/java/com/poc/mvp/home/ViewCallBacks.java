package com.poc.mvp.home;

import com.poc.mvp.model.Conversion;

public interface ViewCallBacks {
    void showProgress();

    void removeProgress();

    void onFailure(Throwable appErrorMessage);

    void getRateSuccess(Conversion conversion);
}
