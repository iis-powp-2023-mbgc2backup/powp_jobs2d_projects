package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

public class ToTextDriver implements Job2dDriver {

    private String text = "";

    public ToTextDriver() {

    }

    public String getText()
    {
        return text;
    }

    @Override
    public void setPosition(int x, int y) {
        text += "SP " + Integer.toString(x) + " " + Integer.toString(y) + "\n";
    }

    @Override
    public void operateTo(int x, int y) {
        text += "OT " + Integer.toString(x) + " " + Integer.toString(y) + "\n";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
