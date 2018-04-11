package com.natinc.oluwatobiloba.medmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.activities.MedicationDetails;
import com.natinc.oluwatobiloba.medmanager.models.Medication;
import com.natinc.oluwatobiloba.medmanager.utils.MedicationListAdapter;
import com.natinc.oluwatobiloba.medmanager.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class MedicationListFragment extends Fragment {

    private static final String TAG = MedicationListFragment.class.getSimpleName();

    FirebaseUser mFirebaseUser;
    FirebaseFirestore mFirestore;

    CollectionReference mColRef;

    MedicationListAdapter mMedicationListAdapter;
    List<Medication> mMedicationList;

    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medication_list, container, false);


        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        mMedicationList = new ArrayList<>();
        mMedicationListAdapter = new MedicationListAdapter(mMedicationList);

        mRecyclerView = rootView.findViewById(R.id.medication_list_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMedicationListAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Medication medication = mMedicationList.get(position);
                Intent intent = new Intent(getActivity(), MedicationDetails.class);
                intent.putExtra(Intent.EXTRA_PACKAGE_NAME, medication);
                intent.putExtra(Intent.EXTRA_UID, medication.medicationId);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mColRef = mFirestore.collection("Users").document(mFirebaseUser.getUid()).collection("Medications");

        mColRef.orderBy("start", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, "Error : " + e.getMessage());
                }

                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        String id = doc.getDocument().getId();

                        Medication medication = doc.getDocument().toObject(Medication.class).withId(id);
                        mMedicationList.add(medication);
                        mMedicationListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        return rootView;
    }
}
