package com.techvipul.medication;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<Medication> loadMedications(Context context) {
        try {
            InputStream is = context.getAssets().open("medications.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Medication>>(){}.getType();
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<String> getCategories(List<Medication> medications) {
        List<String> categories = new ArrayList<>();
        for (Medication med : medications) {
            if (!categories.contains(med.getCategory())) {
                categories.add(med.getCategory());
            }
        }
        return categories;
    }
}