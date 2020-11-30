package com.example.camerabarcodescanner;

public interface MainActivityView {
    void getSuccess (String code, String message);
    void error(String message);
}
