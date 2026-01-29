package com.quicknotes.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quicknotes.app.R;
import com.quicknotes.app.models.Note;

import java.util.List;

/**
 * Custom Adapter for displaying notes in ListView
 * Uses CardView for each note item
 */
public class NotesAdapter extends BaseAdapter {
    
    private Context context;
    private List<Note> notesList;
    private LayoutInflater inflater;

    /**
     * Constructor
     */
    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notesList.get(position).getId();
    }

    /**
     * ViewHolder pattern for efficient ListView scrolling
     */
    static class ViewHolder {
        TextView titleTextView;
        TextView contentPreviewTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            // Inflate the CardView layout
            convertView = inflater.inflate(R.layout.item_note_card, parent, false);
            
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.noteTitle);
            holder.contentPreviewTextView = convertView.findViewById(R.id.noteContentPreview);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        // Get current note
        Note currentNote = notesList.get(position);
        
        // Set data to views
        holder.titleTextView.setText(currentNote.getTitle());
        holder.contentPreviewTextView.setText(currentNote.getContentPreview());
        
        return convertView;
    }

    /**
     * Update the adapter with new data
     */
    public void updateData(List<Note> newNotesList) {
        this.notesList = newNotesList;
        notifyDataSetChanged();
    }
}