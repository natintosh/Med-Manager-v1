package com.natinc.oluwatobiloba.medmanager.utils;

import android.graphics.drawable.GradientDrawable;
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

    public MedicationListAdapter(List<Medication> medicationList) {
        this.mMedicationList = medicationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication medication = mMedicationList.get(position);
        String name = medication.getName();
        String description = medication.getDescription();
        String iconText = String.valueOf(name.charAt(0)).toUpperCase();
        int color = medication.getColor();
        holder.mMedicationName.setText(name);
        holder.mMedicationDescription.setText(description);

        ((GradientDrawable) holder.mIconTextview.getBackground()).setColor(color);
        holder.mIconTextview.setText(iconText);
    }

    @Override
    public int getItemCount() {
        return mMedicationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mMedicationName, mMedicationDescription, mIconTextview;

        ViewHolder(View itemView) {
            super(itemView);
            mMedicationName = itemView.findViewById(R.id.medication_list_name);
            mMedicationDescription = itemView.findViewById(R.id.medication_list_description);
            mIconTextview = itemView.findViewById(R.id.medication_details_icon);
        }
    }
}
