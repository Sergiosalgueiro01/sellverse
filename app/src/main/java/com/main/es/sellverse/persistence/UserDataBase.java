package com.main.es.sellverse.persistence;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.main.es.sellverse.model.Auction;
import com.main.es.sellverse.util.datasavers.TemporalStringSaver;
import com.main.es.sellverse.util.hash.HashGenerator;

import java.util.HashMap;
import java.util.Map;

public class UserDataBase {
    private static FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
    private static FirebaseStorage dbStorage = FirebaseStorage.getInstance();

}
