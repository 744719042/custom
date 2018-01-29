package com.example.custom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button tab;
    private Button horizontal;
    private Button vertical;
    private Button header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = (Button) findViewById(R.id.tabIndicator);
        tab.setOnClickListener(this);
        horizontal = (Button) findViewById(R.id.tabHorizontalSlide);
        horizontal.setOnClickListener(this);
        vertical = (Button) findViewById(R.id.tabVerticalSlide);
        vertical.setOnClickListener(this);
        header = (Button) findViewById(R.id.headerRefresh);
        header.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tab) {
            Intent intent = new Intent(this, TabIndicatorActivity.class);
            startActivity(intent);
        } else if (v == horizontal) {

        } else if (v == vertical) {

        } else if (v == header) {

        }
    }
}
