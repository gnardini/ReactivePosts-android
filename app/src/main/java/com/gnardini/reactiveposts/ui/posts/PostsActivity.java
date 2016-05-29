package com.gnardini.reactiveposts.ui.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.gnardini.reactiveposts.R;
import com.gnardini.reactiveposts.model.Post;
import com.gnardini.reactiveposts.repository.PostsRepository;
import com.gnardini.reactiveposts.ui.rx_new_post.RxNewPostActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.inflate;

public class PostsActivity extends AppCompatActivity {

    @BindView(R.id.posts_toolbar) Toolbar toolbar;
    @BindView(R.id.posts_list) RecyclerView postsList;
    @BindView(R.id.posts_loading) View loadingView;

    private PostsAdapter postsAdapter;
    private PostsRepository postsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity);
        ButterKnife.bind(this);
        postsRepository = new PostsRepository();
        setupToolbar();
        setupRecyclerView();
    }

    private void setupToolbar() {
        toolbar.setTitle("Reactive Inge");
    }

    private void setupRecyclerView() {
        postsList.setHasFixedSize(true);
        postsList.setLayoutManager(new LinearLayoutManager(this));
        postsAdapter = new PostsAdapter();
        postsList.setAdapter(postsAdapter);
        postsRepository.getPosts()
                .doOnNext(post -> {
                    loadingView.setVisibility(View.GONE);
                    postsAdapter.add(post);
                })
                .subscribe();
    }

    @OnClick(R.id.posts_new_post)
    public void onNewPostButtonClicked() {
        startActivity(new Intent(this, RxNewPostActivity.class));
    }

    private static class PostsAdapter extends RecyclerView.Adapter<PostViewHolder> {

        private List<Post> posts;

        public PostsAdapter() {
            this.posts = new LinkedList<>();
        }

        @Override
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PostViewHolder(inflate(parent.getContext(), R.layout.post_adapter, null));
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            Post post = posts.get(position);
            holder.postOwner.setText(post.getOwner());
            holder.postText.setText(post.getText());
            animateIn(holder.itemView);
        }

        private void animateIn(View view) {
            TranslateAnimation anim = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, .0f,
                    Animation.RELATIVE_TO_SELF, .0f,
                    Animation.RELATIVE_TO_SELF, .0f);
            anim.setDuration(300);
            view.startAnimation(anim);
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }

        public void add(Post post) {
            this.posts.add(post);
            notifyItemInserted(posts.size() - 1);
        }

    }

    static class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_owner)
        TextView postOwner;

        @BindView(R.id.post_text)
        TextView postText;

        public PostViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
