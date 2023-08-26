package edu.kis.powp.jobs2d.command;

import java.awt.Shape;

public interface ShapeFactory {

    Shape createRectangleA4(double startX, double startY);

    Shape createRectangleB3(double startX, double startY);

    Shape createCustomRectangle(double startX, double startY, double width, double height);

    Shape createCircle(double startX, double startY, double radius);
}