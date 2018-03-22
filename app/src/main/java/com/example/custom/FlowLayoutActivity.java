package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.custom.widget.FlowLayout;

import java.util.Arrays;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {
    private FlowLayout flowLayout;
    private List<String> mData = Arrays.asList(
            "服务好",
            "材质很好",
            "质量不错",
            "发货快",
            "与描述相符",
            "颜色很美",
            "看着很好",
            "很便宜",
            "包装很好",
            "配件一般"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        flowLayout.setData(mData);
    }
}
