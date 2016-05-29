package com.gnardini.reactiveposts.ui.rx_new_post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gnardini.reactiveposts.R;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class RxNewPostActivity extends AppCompatActivity {

    @BindView(R.id.new_post_toolbar) Toolbar toolbar;
    @BindView(R.id.new_post_owner) EditText postOwner;
    @BindView(R.id.new_post_text) EditText postText;
    @BindView(R.id.new_post_send) View send;
    @BindView(R.id.new_post_loading) View loading;

    private RxNewPostPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post_activity);
        ButterKnife.bind(this);
        presenter = new RxNewPostPresenter(
                getOwnerChanges(),
                getTextChanges());
        presenter.observeSendButtonVisibility()
                .doOnNext(this::setButtonVisible)
                .subscribe();
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar.setTitle("Reactive Inge");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private Observable<CharSequence> getOwnerChanges() {
        return RxTextView.textChanges(postOwner);
    }

    private Observable<CharSequence> getTextChanges() {
        return RxTextView.textChanges(postText);
    }

    @OnClick(R.id.new_post_send)
    void sendPost() {
        setLoadingVisibile(true);
        presenter.sendPost()
            .doOnCompleted(this::finish)
            .doOnError(e -> {
                setLoadingVisibile(false);
                Toast.makeText(RxNewPostActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
            })
            .subscribe();
    }

    private void setButtonVisible(boolean visible) {
        send.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void setLoadingVisibile(boolean visible) {
        loading.setVisibility(visible ? View.VISIBLE: View.GONE);
    }

}
