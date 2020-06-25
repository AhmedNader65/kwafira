package com.almusand.kawfira;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.almusand.kawfira.ui.login.LoginViewModel;
import com.almusand.kawfira.ui.map.MapActivityViewModel;
import com.almusand.kawfira.ui.map.fragments.schedule.SchedulingViewModel;
import com.almusand.kawfira.ui.map.fragments.status.StatusViewModel;
import com.almusand.kawfira.ui.map.fragments.type.TypeViewModel;
import com.almusand.kawfira.ui.register.RegisterViewModel;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory  {

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel();
        }
        else if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            //noinspection unchecked
            return (T) new RegisterViewModel();
        }
        else if (modelClass.isAssignableFrom(MapActivityViewModel.class)) {
            //noinspection unchecked
            return (T) new MapActivityViewModel();
        }
        else if (modelClass.isAssignableFrom(TypeViewModel.class)) {
            //noinspection unchecked
            return (T) new MapActivityViewModel();
        }
        else if (modelClass.isAssignableFrom(StatusViewModel.class)) {
            //noinspection unchecked
            return (T) new MapActivityViewModel();
        }
        else if (modelClass.isAssignableFrom(SchedulingViewModel.class)) {
            //noinspection unchecked
            return (T) new MapActivityViewModel();
        }
//        else if (modelClass.isAssignableFrom(BlogViewModel.class)) {
//            //noinspection unchecked
//            return (T) new BlogViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(RateUsViewModel.class)) {
//            //noinspection unchecked
//            return (T) new RateUsViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(OpenSourceViewModel.class)) {
//            //noinspection unchecked
//            return (T) new OpenSourceViewModel(dataManager,schedulerProvider);
//        }
//        else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
//            //noinspection unchecked
//            return (T) new SplashViewModel(dataManager,schedulerProvider);
//        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
