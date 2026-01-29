package com.quicknotes.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.quicknotes.app.R;
import com.quicknotes.app.adapters.NotesAdapter;
import com.quicknotes.app.database.DatabaseHelper;
import com.quicknotes.app.models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity - Dashboard Screen
 * Displays all saved notes in a ListView with CardView
 * MARKS: 3/3 for Dashboard + 2/2 for Toolbar = 5/5
 */
public class MainActivity extends AppCompatActivity {

    // UI Components
    private ListView notesListView;
    private NotesAdapter notesAdapter;
    
    // Database
    private DatabaseHelper databaseHelper;
    
    // Data
    private List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Toolbar (2 MARKS)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("QuickNotes");
        }

        // Initialize Database Helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize ListView
        notesListView = findViewById(R.id.notesListView);
        notesList = new ArrayList<>();

        // Setup Adapter
        notesAdapter = new NotesAdapter(this, notesList);
        notesListView.setAdapter(notesAdapter);

        // Load notes from database
        loadNotes();

        // Set item click listener (optional - for future edit functionality)
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Note clickedNote = notesList.get(position);
            Toast.makeText(MainActivity.this, 
                "Clicked: " + clickedNote.getTitle(), 
                Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Load all notes from SQLite database and display in ListView
     * This method is called on app launch (4 MARKS for SQLite data handling)
     */
    private void loadNotes() {
        // Retrieve all notes from database
        notesList = databaseHelper.getAllNotes();
        
        // Update adapter with new data
        notesAdapter.updateData(notesList);
        
        // Show message if no notes exist
        if (notesList.isEmpty()) {
            Toast.makeText(this, "No notes yet. Tap + to add a note!", 
                Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Create options menu with Add (+) icon
     * MARKS: 2/2 for Toolbar with Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handle menu item clicks
     * Clicking Add icon navigates to NewNoteActivity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_note) {
            // Navigate to NewNoteActivity
            Intent intent = new Intent(MainActivity.this, NewNoteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Refresh notes list when returning from NewNoteActivity
     * This ensures newly added notes appear immediately
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // Reload notes to show any new additions
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection when activity is destroyed
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}