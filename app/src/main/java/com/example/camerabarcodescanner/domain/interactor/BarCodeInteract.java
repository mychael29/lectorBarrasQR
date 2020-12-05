package com.example.camerabarcodescanner.domain.interactor;

import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;
import com.example.camerabarcodescanner.domain.repository.BarCodeRepository;

public class BarCodeInteract {
    private BarCodeRepository barCodeRepository;

    public BarCodeInteract(BarCodeRepository barCodeRepository) {
        this.barCodeRepository = barCodeRepository;
    }

    public void setBarCodeRepository(String codeBar, BarCodeCallback barCodeCallback) {
        barCodeRepository.barcodeSave(codeBar, barCodeCallback);
    }
}
