package com.example.camerabarcodescanner;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity implements MainActivityView{

    private Button btnScanner;
    private TextView tvBarCode;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);
        btnScanner = findViewById(R.id.btnScanner);
        tvBarCode = findViewById(R.id.tvBarCode);
        btnScanner.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        presenter.sendFirebaseData(result);
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()){
            case R.id.btnScanner:
                new IntentIntegrator(MainActivity.this).initiateScan();
                break;
        }
    };

    @Override
    public void getSuccess(String codeBar, String message) {
        tvBarCode.setText(String.format("%s%s", getString(R.string.message_is), codeBar));
    }

    @Override
    public void error(String message) {
        tvBarCode.setText(message);
    }
}