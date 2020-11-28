package com.almusand.kawfira.ui.map.fragments.status;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.almusand.kawfira.Bases.BaseViewModel;
import com.almusand.kawfira.Models.orders.reservations.OrderModel;
import com.almusand.kawfira.R;
import com.almusand.kawfira.utils.GlobalPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class StatusViewModel extends BaseViewModel<ClientStatusNavigator> {
    OrderModel model;

    public  ObservableField<String> kwafiraName;
    public  ObservableField<String> rate;
    public  ObservableField<String> img;
    private BindableFieldTarget bindableFieldTarget;
    public ObservableField<Drawable> profileImage;

    public StatusViewModel(OrderModel model, Context context) {
        this.model = model;
        try {
            profileImage = new ObservableField<>();
            kwafiraName = new ObservableField<>(model.getKwafera() != null ? model.getKwafera().getName() : "جاري البحث عن كوافيرة");
            rate = new ObservableField<>(model.getKwafera() != null ? String.format("%.2f", Float.valueOf(model.getKwafera().getOverall_rate())) == null ? "لا يوجد تقييم" : getRate(new GlobalPreferences(context).getLanguage(), model.getKwafera().getOverall_rate()) : "");
            img = new ObservableField<>(model.getKwafera().getImage());
            bindableFieldTarget = new BindableFieldTarget(profileImage, context.getResources());
            Picasso.get()
                    .load(img.get())
                    .placeholder(R.drawable.userphoto)
                    .into(bindableFieldTarget);
        }catch (Exception e){

            profileImage = new ObservableField<>();
            kwafiraName = new ObservableField<>();
            rate = new ObservableField<>();
            img = new ObservableField<>();
        }

    }

    private String getRate(String lang,String overall_rate) {
        Log.e("rate", overall_rate);
        float rate = Float.valueOf(overall_rate);
        if(lang.equals("en")) {
            if (rate > 4) {
                return "Excellent";
            } else if (rate > 3) {
                return "Very good";
            } else if (rate > 2) {
                return "Good";
            } else {
                return "Acceptable";
            }
        }else{
            if (rate > 4) {
                return "ممتاز";
            } else if (rate > 3) {
                return "جيد جداً";
            } else if (rate > 2) {
                return "جيد";
            } else {
                return "مقبول";
            }
        }
    }

    @BindingAdapter({"bind:img"})
    public static void loadImage(ImageView view, String img) {
        Picasso.get()
                .load(img)
                .placeholder(R.drawable.userphoto)
                .into(view);
    }


    public class BindableFieldTarget implements Target {

        private ObservableField<Drawable> observableField;
        private Resources resources;

        public BindableFieldTarget(ObservableField<Drawable> observableField, Resources resources) {
            this.observableField = observableField;
            this.resources = resources;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            observableField.set(new BitmapDrawable(resources, bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            observableField.set(errorDrawable);

        }


        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            observableField.set(placeHolderDrawable);
        }
    }
}
