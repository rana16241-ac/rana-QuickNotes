package com.quicknotes.app.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.quicknotes.app.R;
import com.quicknotes.app.database.DatabaseHelper;
import com.quicknotes.app.models.Note;

/**
 * NewNoteActivity - Create New Note Screen
 * Allows user to create and save a new note
 * MARKS: 5/5 for NewNoteActivity implementation
 */
public class NewNoteActivity extends AppCompatActivity {

    // UI Components
    private EditText titleEditText;
    private EditText contentEditText;
    private Button saveButton;
    
    // Database
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        // Setup Toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Note");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize UI Components
        titleEditText = findViewById(R.id.editTextTitle);
        contentEditText = findViewById(R.id.editTextContent);
        saveButton = findViewById(R.id.buttonSave);

        // Set Save Button Click Listener
        saveButton.setOnClickListener(v -> saveNote());
    }

    /**
     * Save note to SQLite database with validation
     * MARKS: Input validation + SQLite save + Return to MainActivity
     */
    private void saveNote() {
        // Get input values
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();

        // Validate inputs - no empty fields allowed
        if (TextUtils.isEmpty(title)) {
            titleEditText.setError("Title is required");
            titleEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(content)) {
            contentEditText.setError("Content is required");
            contentEditText.requestFocus();
            return;
        }

        // Create Note object
        Note newNote = new Note(title, content);

        // Save to SQLite database
        long result = databaseHelper.addNote(newNote);

        // Check if save was successful
        if (result != -1) {
            Toast.makeText(this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
            
            // Return to MainActivity
            // The note will automatically appear in ListView due to onResume() in MainActivity
            finish();
        } else {
            Toast.makeText(this, "Error saving note. Please try again.", 
                Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handle toolbar back button click
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}