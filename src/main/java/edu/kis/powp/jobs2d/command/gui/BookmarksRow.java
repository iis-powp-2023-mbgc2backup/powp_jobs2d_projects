package edu.kis.powp.jobs2d.command.gui;

import java.awt.Container;

import javax.swing.*;

public class BookmarksRow 
{
    private JTextArea bookmarkName; 
    private JTextArea bookmarkDescription;
    private JButton btnAddBookmark; 
    private JButton btnRemoveBookmark;

    BookmarksRow(JTextArea bookmarkName, JTextArea bookmarkDescription, JButton btnAddBookmark, JButton btnRemoveBookmark)
    {
        this.btnAddBookmark = btnAddBookmark;
        this.bookmarkDescription = bookmarkDescription;
        this.bookmarkName = bookmarkName;
        this.btnRemoveBookmark = btnRemoveBookmark;
    }

    public void removeRow(Container content)
    {
        content.remove(bookmarkName);
        content.remove(bookmarkDescription);
        content.remove(btnAddBookmark);
        content.remove(btnRemoveBookmark);
    }

    public String getNameOfBookmark()
    {
        return bookmarkName.getText();
    }
    
}
