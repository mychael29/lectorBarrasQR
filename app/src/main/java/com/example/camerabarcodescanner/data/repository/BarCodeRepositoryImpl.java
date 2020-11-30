package com.example.camerabarcodescanner.data.repository;

import com.example.camerabarcodescanner.data.entity.Codigo;
import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;
import com.example.camerabarcodescanner.domain.repository.BarCodeRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentResult;

import java.util.UUID;

import static com.example.camerabarcodescanner.data.DataConstants.CODE_PARAMETERS;
import static com.example.camerabarcodescanner.data.DataConstants.SUCCESS;

public class BarCodeRepositoryImpl implements BarCodeRepository {
    private Codigo codigo;
    private final FirebaseDatabase database;

    public BarCodeRepositoryImpl(IntentResult intentResult) {
        codigo = new Codigo(intentResult.getContents());
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void getBarcodeSave(String codeBar, BarCodeCallback callback) {
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child(CODE_PARAMETERS).child(UUID.randomUUID().toString()).setValue(codigo);
        callback.barCodeSuccess(codeBar, SUCCESS);
    }
}
