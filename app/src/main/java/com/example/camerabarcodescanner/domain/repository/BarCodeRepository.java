package com.example.camerabarcodescanner.domain.repository;

import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;

public interface BarCodeRepository {
    void getBarcodeSave(String codeBar, BarCodeCallback callback);
}
