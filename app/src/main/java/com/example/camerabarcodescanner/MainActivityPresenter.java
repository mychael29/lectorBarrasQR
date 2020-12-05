package com.example.camerabarcodescanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.camerabarcodescanner.data.entity.Codigo;
import com.example.camerabarcodescanner.data.repository.BarCodeRepositoryImpl;
import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;
import com.example.camerabarcodescanner.domain.interactor.BarCodeInteract;
import com.example.camerabarcodescanner.domain.repository.BarCodeRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static com.example.camerabarcodescanner.data.DataConstants.CODES_BARS_LIST;
import static com.example.camerabarcodescanner.data.DataConstants.ERROR_CODE_BAR;
import static com.example.camerabarcodescanner.data.DataConstants.PREFERENCE;
import static com.example.camerabarcodescanner.data.DataConstants.ZERO_CODE_BAR;

public class MainActivityPresenter implements BarCodeCallback {
    private final MainActivityView mainActivityView;
    private Context context;
    private ArrayList<Codigo> codes = new ArrayList<>();

    public MainActivityPresenter(MainActivityView mainActivityView, Context context) {
        this.mainActivityView = mainActivityView;
        this.context = context;
    }

    @Override
    public void barCodeSuccess(String codeBar, String message) {
        mainActivityView.success(codeBar);
    }

    @Override
    public void barCodeError(String message) {
        mainActivityView.success(message);

    }

    public void sendFirebaseData(List<Codigo> codeList) {
        StringBuilder codesBars = new StringBuilder();
        ArrayList<Codigo> newsCodes = new ArrayList<>();
        for (Codigo codigo: codeList) {
            if (!codigo.isUpload()) {
                newsCodes.add(codigo);
                codesBars.append("\n").append(codigo.valor);
                codigo.setUpload(true);
            }
        }
        BarCodeRepository barCodeRepository = new BarCodeRepositoryImpl(newsCodes);
        BarCodeInteract barCodeInteract = new BarCodeInteract(barCodeRepository);
        barCodeInteract.setBarCodeRepository(codesBars.toString(), this);
        codes.clear();
        codes.addAll(codeList);
        saveLocalData(null);
    }

    public void saveLocalData(Codigo codigo) {
        SharedPreferences.Editor editor = this.context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit();
        if (codigo != null) codes.add(codigo);
        Gson gson = new Gson();
        String codesBarsString = gson.toJson(codes);
        editor.putString(CODES_BARS_LIST, codesBarsString);
        editor.commit();
    }

    public ArrayList<Codigo> receiveDataLocal() {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE,Context.MODE_PRIVATE);
        String codesBarString = pref.getString(CODES_BARS_LIST,"");
        ArrayList<Codigo> list = null;
        if (!codesBarString.isEmpty()) {
            Gson gson = new Gson();
            TypeToken<List<Codigo>> token = new TypeToken<List<Codigo>>() {};
            list = gson.fromJson(codesBarString, token.getType());
        }
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }
}
