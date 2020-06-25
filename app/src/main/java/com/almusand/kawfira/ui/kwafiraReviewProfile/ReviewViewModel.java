package com.almusand.kawfira.ui.kwafiraReviewProfile;

import androidx.databinding.ObservableField;

public class ReviewViewModel {
    public final ObservableField<String> author;

    public final ObservableField<String> content;

    public final ObservableField<String> date;

    public final ObservableField<String> imageUrl;


    private final ReviewModel model;

    public ReviewViewModel(ReviewModel model) {
        this.model = model;
        imageUrl = new ObservableField<>(model.getReviewer().getImage());
        date = new ObservableField<>(model.getDate().substring(0,10));
        author = new ObservableField<>(model.getReviewer().getName());
        content = new ObservableField<>(model.getContent());
    }
}
