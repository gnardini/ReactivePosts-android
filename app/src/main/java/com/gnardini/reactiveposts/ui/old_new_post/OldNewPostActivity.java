package com.gnardini.reactiveposts.ui.old_new_post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gnardini.reactiveposts.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class OldNewPostActivity extends AppCompatActivity implements OldNewPostView {

    @BindView(R.id.new_post_send) View send;

    private OldNewPostPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post_activity);
        ButterKnife.bind(this);
        presenter = new OldNewPostPresenter(this);
    }

    @OnTextChanged(R.id.new_post_owner)
    void onOwnerChanged(CharSequence owner) {
        presenter.onOwnerChanged(owner.toString());
    }

    @OnTextChanged(R.id.new_post_text)
    void onTextChanged(CharSequence text) {
        presenter.onTextChanged(text.toString());
    }

    public void setButtonVisible(boolean visible) {
        send.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

}
