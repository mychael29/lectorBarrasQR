package com.example.camerabarcodescanner.presentation.ui;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camerabarcodescanner.R;
import com.example.camerabarcodescanner.data.entity.Codigo;
import com.example.camerabarcodescanner.presentation.adapters.CodesBarsAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static com.example.camerabarcodescanner.data.DataConstants.ERROR_CODE_BAR;


public class MainActivity extends AppCompatActivity implements MainActivityView{

    private Button btnScanner;
    private Button btnSend;
    private TextView tvBarCode;
    private MainActivityPresenter presenter;
    private RecyclerView recyclerView;
    private CodesBarsAdapter codesBarsAdapter;
    private ArrayList<Codigo> codesBarsList;
    private ProgressDialog mProgressDialog;
    private boolean codebarsloca = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this, this);
        btnScanner = findViewById(R.id.btnScanner);
        btnSend = findViewById(R.id.btnSend);
        tvBarCode = findViewById(R.id.tvBarCode);
        recyclerView = findViewById(R.id.rvCodesBars);
        btnScanner.setOnClickListener(mOnClickListener);
        btnSend.setOnClickListener(mOnClickListener);
        codesBarsAdapter = new CodesBarsAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        codesBarsList = presenter.receiveDataLocal();
        codesBarsAdapter.loadItems(codesBarsList);
        recyclerView.setAdapter(codesBarsAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            Codigo codigo = new Codigo(result.getContents());
            codesBarsList.add(codigo);
            codesBarsAdapter.loadItems(codesBarsList);
            btnSend.setEnabled(true);
            presenter.saveLocalData(codigo);
        } else {
            errorCapture(ERROR_CODE_BAR);
        }
    }

    private View.OnClickListener mOnClickListener = v -> {
        switch (v.getId()) {
            case R.id.btnScanner:
                new IntentIntegrator(MainActivity.this).initiateScan();
                break;
            case R.id.btnSend:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Enviando lote...");
                mProgressDialog.show();
                presenter.sendFirebaseData(codesBarsList);
                btnSend.setEnabled(false);
                break;
        }
    };

    @Override
    public void success(String message) {
        codesBarsAdapter.loadItems(presenter.receiveDataLocal());
        tvBarCode.setText(String.format("%s%s", getString(R.string.message_is), message));
        mProgressDialog.dismiss();
    }

    @Override
    public void errorCapture(String message) {
        tvBarCode.setText(message);
    }
}