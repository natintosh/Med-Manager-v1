package com.natinc.oluwatobiloba.medmanager.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.models.Medication;
import com.natinc.oluwatobiloba.medmanager.utils.MedicationListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MedicationListFragment extends Fragment {

    private static final String TAG = MedicationListFragment.class.getSimpleName();

    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirestore;

    CollectionReference mColRef;

    MedicationListAdapter mMedicationListAdapter;
    List<Medication> mMedicationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medication_list, container, false);


        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        mMedicationList = new ArrayList<>();
        mMedicationListAdapter = new MedicationListAdapter(mMedicationList);

        mColRef = mFirestore.collection("Users").document(mFirebaseUser.getUid()).collection("Medications");

//        mColRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d(TAG, document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d(TAG, "Error getting documents: ", task.getException());
//                }
//            }
//        });

        mColRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, "Error : " + e.getMessage());
                }

//                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
////                    if (doc.getType() == DocumentChange.Type.ADDED) {
////
////                        Medication medication = doc.getDocument().toObject(Medication.class);
////                        mMedicationList.add(medication);
////                        mMedicationListAdapter.notifyDataSetChanged();
////                    }
////                }
            }
        });

        return rootView;
    }
}
