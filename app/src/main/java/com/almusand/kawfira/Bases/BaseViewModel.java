package com.almusand.kawfira.Bases;

import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableBoolean;
import io.reactivex.disposables.CompositeDisposable;
import java.lang.ref.WeakReference;

/**
 * Created by amitshekhar on 07/07/17.
 */

public abstract class BaseViewModel<N> extends ViewModel {

//    private final DataManager mDataManager;

    private final ObservableBoolean mIsLoading = new ObservableBoolean();

//    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mNavigator;
    private WeakReference<N> mNavigator2;

//    public BaseViewModel(DataManager dataManager,
//                         SchedulerProvider schedulerProvider) {
//        this.mDataManager = dataManager;
//        this.mSchedulerProvider = schedulerProvider;
//        this.mCompositeDisposable = new CompositeDisposable();
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

//    public DataManager getDataManager() {
//        return mDataManager;
//    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator.get();
    }
    public N getNavigator2() {
        return mNavigator2.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }
    public void setNavigator2(N navigator) {
        this.mNavigator2 = new WeakReference<>(navigator);
    }


//    public SchedulerProvider getSchedulerProvider() {
//        return mSchedulerProvider;
//    }
}