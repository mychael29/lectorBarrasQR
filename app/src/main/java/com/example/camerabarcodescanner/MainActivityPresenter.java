package com.example.camerabarcodescanner;

import com.example.camerabarcodescanner.data.repository.BarCodeRepositoryImpl;
import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;
import com.example.camerabarcodescanner.domain.interactor.BarCodeInteract;
import com.example.camerabarcodescanner.domain.repository.BarCodeRepository;
import com.google.zxing.integration.android.IntentResult;

import static com.example.camerabarcodescanner.data.DataConstants.ERROR_CODE_BAR;

public class MainActivityPresenter implements BarCodeCallback {
    private final MainActivityView mainActivityView;

    public MainActivityPresenter(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    @Override
    public void barCodeSuccess(String codeBar, String message) {
        mainActivityView.getSuccess(codeBar, message);
    }

    public void sendFirebaseData(IntentResult intentResult) {
        if (intentResult != null && intentResult.getContents() != null) {
            BarCodeRepository barCodeRepository = new BarCodeRepositoryImpl(intentResult);
            BarCodeInteract barCodeInteract = new BarCodeInteract(barCodeRepository);
            barCodeInteract.setBarCodeRepository(intentResult.getContents(), this);
        } else {
            mainActivityView.error(ERROR_CODE_BAR);
        }
    }
}
