package com.example.camerabarcodescanner.data.entity;

public class Codigo {

    public String valor;
    private boolean upload = false;

    public Codigo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Codigo(String valor) {
        this.valor = valor;

    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }
}
