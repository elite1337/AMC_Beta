package com.example.pete.amc;

public class FavoriteRow {

    private String title;
    private long noteId, dateCreatedMilli;
    private Category category;

    public enum Category{ONE, TWO, THREE}

    public FavoriteRow(String title, Category category) {
        this.title = title;
        this.category = category;
        this.noteId = 0;
        this.dateCreatedMilli = 0;
    }

    public FavoriteRow(String title, long noteId, long dateCreatedMilli, Category category) {
        this.title = title;
        this.noteId = noteId;
        this.dateCreatedMilli = dateCreatedMilli;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public long getDateCreatedMilli() {
        return dateCreatedMilli;
    }

    public void setDateCreatedMilli(long dateCreatedMilli) {
        this.dateCreatedMilli = dateCreatedMilli;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String toString()
    {
        return "ID: "+noteId+ "Title: "+title+ "IconID: "+category.name()+ "Date: ";
    }

    public int getAssociatedDrawable()
    {
        return categoryToDrawable(category);
    }

    public static int categoryToDrawable(Category noteCategory)
    {
        switch (noteCategory)
        {
            case ONE:
                return R.drawable.ic_explore_black_24dp;
            case TWO:
                return R.drawable.facebook;
            case THREE:
                return R.drawable.ic_email_black_24dp;
        }

        return R.drawable.ic_explore_black_24dp;
    }
}
