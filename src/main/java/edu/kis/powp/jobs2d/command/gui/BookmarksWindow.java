package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.Bookmarks;
import edu.kis.powp.jobs2d.command.DriverCommand;

public class BookmarksWindow extends JFrame implements WindowComponent, BookmarksGui
{
    private Bookmarks bookmarks;
    private Container content;
    private GridBagConstraints gridBagConstraints;

    private Integer rowNumber = 0;
    private JTextArea textInput;
    private String defaultTextInputMessage = "Write here to add description to a bookmark";
    private LinkedList<BookmarksRow> savedBookmarks = new LinkedList<BookmarksRow>();
    private ActionListener defaultDeleteActionListener = new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    bookmarks.deleteBookmark(event.getActionCommand());
                }
            };

    public BookmarksWindow() {
        bookmarks = Bookmarks.getInstance();
        this.setTitle("Bookmarks");
        this.setSize(800, 400);
        content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();

        JButton btnAddBookmark = new JButton("Make a bookmark of the current command");
        btnAddBookmark.addActionListener((ActionEvent e) -> Bookmarks.getInstance().bookmarkCurrentCommand());
        gridBagConstraints.gridy = 0;
        content.add(btnAddBookmark, gridBagConstraints);

        textInput = new JTextArea(defaultTextInputMessage);
        textInput.setEditable(true);
        gridBagConstraints.gridy = 1;
        content.add(textInput, gridBagConstraints);
    }

    @Override
    public void displayFailiureStatus()
    {
        String errorResponse = "Could not save the current command\n" + defaultTextInputMessage;
        textInput.setText(errorResponse);
    }

    @Override
    public void displayFailiureStatusDuplicate()
    {
        String errorResponse = "Current command is already bookmarked\n" + defaultTextInputMessage;
        textInput.setText(errorResponse);
    }

    @Override
    public String getDescription()
    {
        return textInput.getText();
    }


    @Override
    public void update()
    {
        LinkedList<DriverCommand> commands = bookmarks.getBookmarkedCommands();
        LinkedList<String> descriptions = bookmarks.getBookmarkedDescriptions();

        Iterator<DriverCommand> currentCommandsIterator = commands.iterator();
        int i = 0;
        for (BookmarksRow row : savedBookmarks)
        {
            if (currentCommandsIterator.hasNext())
            {
                DriverCommand command = currentCommandsIterator.next();
                if (!command.toString().equals(row.getNameOfBookmark()))
                {
                    row.removeRow(content);
                    savedBookmarks.remove(row);
                    CommandLoaderListener action = new CommandLoaderListener(command, bookmarks.getCommandManager());
                    addBookmark(command.toString(), descriptions.get(i), action);
                    update();
                    return;
                }
            }
            else
            {
                row.removeRow(content);
                savedBookmarks.remove(row);
                update();
                return;
            }
            i += 1;
        }

        while(currentCommandsIterator.hasNext())
        {
            DriverCommand command = currentCommandsIterator.next();
            CommandLoaderListener action = new CommandLoaderListener(command, bookmarks.getCommandManager());
            addBookmark(command.toString(), descriptions.get(i), action);
            i += 1;
        }

        SwingUtilities.updateComponentTreeUI(this);
    }

    public void addBookmark(String name, String description, CommandLoaderListener action) 
    {
        try
        {
            JTextArea bookmarkName = new JTextArea(name);
            gridBagConstraints.weightx = 1;
            gridBagConstraints.gridy = rowNumber + 2;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weighty = 1;
            content.add(bookmarkName, gridBagConstraints);
            
            JTextArea bookmarkDescription = new JTextArea(description);
            gridBagConstraints.gridx = 1;
            content.add(bookmarkDescription, gridBagConstraints);

            JButton btnAddBookmark = new JButton("Load command");
            btnAddBookmark.addActionListener(action);
            gridBagConstraints.gridx = 2;
            content.add(btnAddBookmark, gridBagConstraints);

            JButton btnRemoveBookmark = new JButton("Delete bookmark");
            btnRemoveBookmark.setActionCommand(name);
            btnRemoveBookmark.addActionListener(defaultDeleteActionListener);
            gridBagConstraints.gridx = 3;
            content.add(btnRemoveBookmark, gridBagConstraints);

            savedBookmarks.add(new BookmarksRow(bookmarkName, bookmarkDescription, btnAddBookmark, btnRemoveBookmark));

            rowNumber++;
            textInput.setText(defaultTextInputMessage);
        }
        catch (Exception e)
        {
            displayFailiureStatus();
        }        
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() 
    {
        if (!this.isVisible()) {
            textInput.setText(defaultTextInputMessage);
        }
        this.setVisible(!this.isVisible());
    }
}