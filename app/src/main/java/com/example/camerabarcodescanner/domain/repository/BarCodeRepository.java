package com.example.camerabarcodescanner.domain.repository;

import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;

public interface BarCodeRepository {
    void barcodeSave(String codeBar, BarCodeCallback callback);
}
