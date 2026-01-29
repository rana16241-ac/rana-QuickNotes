package com.quicknotes.app.models;

/**
 * Note Model Class
 * Represents a single note with id, title, and content
 */
public class Note {
    private int id;
    private String title;
    private String content;

    // Constructor with all fields
    public Note(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Constructor without id (for new notes)
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get preview of content (first 50 characters)
     * Used for displaying in ListView
     */
    public String getContentPreview() {
        if (content.length() > 50) {
            return content.substring(0, 50) + "...";
        }
        return content;
    }
}