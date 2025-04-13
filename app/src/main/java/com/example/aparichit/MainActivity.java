package com.example.aparichit;

import android.os.Bundle;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText etProblemName, etCauseName, etLocation;
    Button btnAction;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();

        // Connect views
        etProblemName = findViewById(R.id.username);
        etCauseName = findViewById(R.id.problem);
        etLocation = findViewById(R.id.location);
        btnAction = findViewById(R.id.btnLogin);

        // Button click listener
        btnAction.setOnClickListener(v -> {
            String problemName = etProblemName.getText().toString().trim();
            String causeName = etCauseName.getText().toString().trim();
            String location = etLocation.getText().toString().trim();

            // Validate
            if (problemName.isEmpty() || causeName.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Data map
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("Name Of Problem : ", problemName);
            userMap.put("Problem Occurs Due To : ", causeName);
            userMap.put("Location Of Criminal : ", location);

            // Send to Firestore
            firestore.collection("user")
                    .add(userMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Data submitted successfully", Toast.LENGTH_SHORT).show();

                            // Clear fields
                            etProblemName.setText("");
                            etCauseName.setText("");
                            etLocation.setText("");
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }
}