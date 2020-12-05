package com.example.camerabarcodescanner.domain.callback;

public interface BarCodeCallback {
    void barCodeSuccess(String code, String message);
    void barCodeError(String message);
}
