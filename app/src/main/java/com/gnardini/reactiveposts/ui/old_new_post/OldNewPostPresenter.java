package com.gnardini.reactiveposts.ui.old_new_post;

import android.text.TextUtils;

public class OldNewPostPresenter {

    private OldNewPostView view;
    private String owner;
    private String text;

    public OldNewPostPresenter(OldNewPostView view) {
        this.view = view;
    }

    public void onOwnerChanged(String owner) {
        this.owner = owner;
        checkConditionsAreMet();
    }

    public void onTextChanged(String text) {
        this.text = text;
        checkConditionsAreMet();
    }

    private void checkConditionsAreMet() {
        view.setButtonVisible(!TextUtils.isEmpty(owner) && !TextUtils.isEmpty(text));
    }

}
