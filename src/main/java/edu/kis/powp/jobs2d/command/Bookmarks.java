package edu.kis.powp.jobs2d.command;

import java.util.LinkedList;

import edu.kis.powp.jobs2d.command.gui.BookmarksGui;
import edu.kis.powp.jobs2d.command.gui.DuplicateBookmarkException;
import edu.kis.powp.jobs2d.command.manager.CommandManager;

public class Bookmarks {
    private static Bookmarks instance = null;
    private String defaultTextInputMessage = "Write here to add description to a bookmark";

    private CommandManager commandManager = null;
    private BookmarksGui gui = null;

    private LinkedList<DriverCommand> bookmarkedCommands = new LinkedList<DriverCommand>();
    private LinkedList<String> bookmarkedDescriptions = new LinkedList<String>();

    public static Bookmarks getInstance()
    {
        if (instance == null)
        {
            instance = new Bookmarks();
        }

        return instance;
    }

    private Bookmarks() {}

    public BookmarksGui getGui()
    {
        return gui;
    }

    public void setGui(BookmarksGui gui)
    {
        this.gui = gui;
    }

    public void setCommandManager(CommandManager commandManager)
    {
        this.commandManager = commandManager;
    }

    private void addBookmark(DriverCommand command, String description)
    {
        bookmarkedCommands.add(command);
        bookmarkedDescriptions.add(description);
        gui.update();
    }

    public CommandManager getCommandManager()
    {
        return commandManager;
    }

    public LinkedList<String> getBookmarkedDescriptions()
    {
        return bookmarkedDescriptions;
    }

    public LinkedList<DriverCommand> getBookmarkedCommands()
    {
        return bookmarkedCommands;
    }

    private int findIndexOfName(String name)
    {
        int i = 0;
        for (DriverCommand c : bookmarkedCommands)
        {
            if (name.equals(c.toString()))
            {
                return i;
            }

            i += 1;
        }

        return -1;
    }

    public void deleteBookmark(String bookmarkName)
    {
        int index = findIndexOfName(bookmarkName);
        bookmarkedCommands.remove(index);
        bookmarkedDescriptions.remove(index);
        gui.update();
    }

    public void bookmarkCurrentCommand()
    {
        try
        {
            DriverCommand command = commandManager.getCurrentCommand();
            if (command == null)
            {
                throw new Exception();
            }
            else if (bookmarkedCommands.contains(command))
            {
                throw new DuplicateBookmarkException();
            }
            
            String description = gui.getDescription();
            if (description.equals(defaultTextInputMessage)|| description.equals(""))
            {
                description = "No description";
            }

            addBookmark(command, description);
        }
        catch (DuplicateBookmarkException ex)
        {
            gui.displayFailiureStatusDuplicate();
        }
        catch(Exception e)
        {
            gui.displayFailiureStatus();
        }
        


    }
    
}
