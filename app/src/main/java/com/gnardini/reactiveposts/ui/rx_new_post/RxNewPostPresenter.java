package com.gnardini.reactiveposts.ui.rx_new_post;

import android.text.TextUtils;

import com.gnardini.reactiveposts.model.Post;
import com.gnardini.reactiveposts.repository.PostsRepository;

import rx.Observable;

public class RxNewPostPresenter {

    private final PostsRepository postsRepository;
    private Observable<Boolean> sendButtonVisibility;

    private String owner;
    private String text;

    public RxNewPostPresenter(
            Observable<CharSequence> ownerObservable,
            Observable<CharSequence> textObservable) {
        postsRepository = new PostsRepository();
        ownerObservable = ownerObservable.doOnNext(s -> owner = s.toString());
        textObservable = textObservable.doOnNext(s -> text = s.toString());
        observeTextChanges(ownerObservable, textObservable);
    }

    public Observable<Void> sendPost() {
        return postsRepository.postNew(getPost());
    }

    public Observable<Boolean> observeButtonVisibility() {
        return sendButtonVisibility;
    }

    private void observeTextChanges(
            Observable<CharSequence> ownerObservable,
            Observable<CharSequence> textObservable) {
        sendButtonVisibility =
                Observable.combineLatest(ownerObservable, textObservable, this::isValidCombination);
    }

    private boolean isValidCombination(CharSequence owner, CharSequence text) {
        return !TextUtils.isEmpty(owner) && !TextUtils.isEmpty(text);
    }

    private Post getPost() {
        return new Post(owner, text);
    }

}
