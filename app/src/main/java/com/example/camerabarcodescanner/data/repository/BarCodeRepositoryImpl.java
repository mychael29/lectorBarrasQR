package com.example.camerabarcodescanner.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.camerabarcodescanner.data.entity.Codigo;
import com.example.camerabarcodescanner.domain.callback.BarCodeCallback;
import com.example.camerabarcodescanner.domain.repository.BarCodeRepository;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.camerabarcodescanner.data.DataConstants.CODE_PARAMETERS;
import static com.example.camerabarcodescanner.data.DataConstants.SUCCESS;

public class BarCodeRepositoryImpl implements BarCodeRepository {
    private ArrayList<Codigo> codigos;
    private final FirebaseDatabase database;

    public BarCodeRepositoryImpl(ArrayList<Codigo> codigos) {
        this.codigos = codigos;
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public void barcodeSave(String codeBar, BarCodeCallback callback) {
        DatabaseReference databaseReference = database.getReference();
        if (codigos.size() > 0) {

            databaseReference.child(CODE_PARAMETERS).child(UUID.randomUUID().toString()).setValue(codigos).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()){
                            callback.barCodeSuccess(codeBar, SUCCESS);
                        }else {
                            callback.barCodeError("Error al enviar a la nube firestore.");
                        }
                    }
            );
        } else {
            callback.barCodeError("Todos los codigos de barras estan en la nube.");
        }
    }
}
