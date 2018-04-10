package com.natinc.oluwatobiloba.medmanager.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.natinc.oluwatobiloba.medmanager.R;
import com.natinc.oluwatobiloba.medmanager.models.Medication;

import java.util.List;

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder> {

    List<Medication> mMedicationList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMedicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mMedicationTitle, mMedicationDescription;

        ViewHolder(View itemView) {
            super(itemView);
            mMedicationTitle = itemView.findViewById(R.id.medication_list_title);
            mMedicationDescription = itemView.findViewById(R.id.medication_list_description);
        }
    }
}
