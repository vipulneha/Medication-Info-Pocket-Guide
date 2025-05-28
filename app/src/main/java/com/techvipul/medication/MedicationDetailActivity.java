package com.techvipul.medication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.List;

public class MedicationDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());

        setContentView(R.layout.activity_medication_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String medicationName = getIntent().getStringExtra("medication_name");
        List<Medication> medications = DataLoader.loadMedications(this);
        Medication selectedMedication = null;
        for (Medication med : medications) {
            if (med.getName().equals(medicationName)) {
                selectedMedication = med;
                break;
            }
        }

        if (selectedMedication != null) {
            getSupportActionBar().setTitle(selectedMedication.getName());
            TextView genericName = findViewById(R.id.detail_generic_name);
            TextView category = findViewById(R.id.detail_category);
            TextView use = findViewById(R.id.detail_use);
            TextView dosage = findViewById(R.id.detail_dosage);
            TextView sideEffects = findViewById(R.id.detail_side_effects);
            TextView warnings = findViewById(R.id.detail_warnings);

            genericName.setText(selectedMedication.getGenericName());
            category.setText(selectedMedication.getCategory());
            use.setText(selectedMedication.getUse());
            dosage.setText(selectedMedication.getDosage());
            sideEffects.setText(selectedMedication.getSideEffects());
            warnings.setText(selectedMedication.getWarnings());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}