package com.techvipul.medication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CategoryAdapter adapter;
    private List<String> categories;
    private List<Medication> medications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medication Info");

        RecyclerView recyclerView = findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medications = DataLoader.loadMedications(this);
        categories = DataLoader.getCategories(medications);
        adapter = new CategoryAdapter(categories, medications);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search categories...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        return true;
    }

    private class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
        private List<String> categories;
        private List<String> filteredCategories;
        private List<Medication> medications;

        CategoryAdapter(List<String> categories, List<Medication> medications) {
            this.categories = categories;
            this.medications = medications;
            this.filteredCategories = new ArrayList<>(categories);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medication_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String category = filteredCategories.get(position);
            holder.nameTextView.setText(category);
            holder.descriptionTextView.setText("Browse medications in this category");
            holder.cardView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, MedicationListActivity.class);
                intent.putExtra("category", category);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return filteredCategories.size();
        }

        public void filter(String query) {
            filteredCategories.clear();
            if (query.isEmpty()) {
                filteredCategories.addAll(categories);
            } else {
                for (String category : categories) {
                    if (category.toLowerCase().contains(query.toLowerCase())) {
                        filteredCategories.add(category);
                    }
                }
            }
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView, descriptionTextView;
            CardView cardView;

            ViewHolder(View view) {
                super(view);
                nameTextView = view.findViewById(R.id.medication_name);
                descriptionTextView = view.findViewById(R.id.medication_description);
                cardView = view.findViewById(R.id.medication_card);
                view.findViewById(R.id.medication_category).setVisibility(View.GONE);
            }
        }
    }
}