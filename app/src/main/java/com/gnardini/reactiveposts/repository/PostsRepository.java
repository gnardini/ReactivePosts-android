package com.gnardini.reactiveposts.repository;

import com.gnardini.reactiveposts.model.Post;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rx.Observable;
import rx.schedulers.Schedulers;

public class PostsRepository {

    DatabaseReference postsRef;

    public PostsRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        postsRef = database.getReference("posts");
    }

    public Observable<Post> getPosts() {
        return Observable.<Post>create(s -> {
            postsRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previous) {
                    if (!s.isUnsubscribed()) {
                        s.onNext(snapshot.getValue(Post.class));
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    if (!s.isUnsubscribed()) {
                        s.onError(databaseError.toException());
                    }
                }
            });
        }).subscribeOn(Schedulers.io());
    }

    public Observable<Void> postNew(Post post) {
        return Observable.<Void>create(s -> {
            postsRef.push().setValue(post, (error, dbRef) -> {
                if (s.isUnsubscribed()) {
                    return;
                }
                if (error != null) {
                    s.onError(error.toException());
                } else {
                    s.onCompleted();
                }
            });
        }).subscribeOn(Schedulers.io());
    }

}
