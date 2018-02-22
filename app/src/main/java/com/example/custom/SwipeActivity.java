package com.example.custom;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.custom.adapter.UserListAdapter;

public class SwipeActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnChildScrollUpCallback {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new UserListAdapter(this));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnChildScrollUpCallback(this);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
                final AbsListView absListView = listView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
        } else {
            return ViewCompat.canScrollVertically(listView, -1);
        }
    }
}
