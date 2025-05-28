package com.techvipul.medication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.ViewHolder> {
    private List<Medication> medications;
    private List<Medication> filteredMedications;
    private Context context;

    public MedicationAdapter(Context context, List<Medication> medications) {
        this.context = context;
        this.medications = medications;
        this.filteredMedications = new ArrayList<>(medications);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication medication = filteredMedications.get(position);
        holder.nameTextView.setText(medication.getName());
        holder.categoryTextView.setText(medication.getCategory());
        holder.descriptionTextView.setText(medication.getUse());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MedicationDetailActivity.class);
            intent.putExtra("medication_name", medication.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredMedications.size();
    }

    public void filter(String query) {
        filteredMedications.clear();
        if (query.isEmpty()) {
            filteredMedications.addAll(medications);
        } else {
            for (Medication med : medications) {
                if (med.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredMedications.add(med);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView, descriptionTextView;
        CardView cardView;

        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.medication_name);
            categoryTextView = view.findViewById(R.id.medication_category);
            descriptionTextView = view.findViewById(R.id.medication_description);
            cardView = view.findViewById(R.id.medication_card);
        }
    }
}