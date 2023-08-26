package edu.kis.powp.jobs2d.command;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

public class StandardShapeFactory implements ShapeFactory {

    @Override
    public Shape createRectangleA4(double startX, double startY) {
        return new Rectangle2D.Double(startX, startY, 210, 297); // Dimensions of A4 paper in mm
    }

    @Override
    public Shape createRectangleB3(double startX, double startY) {
        return new Rectangle2D.Double(startX, startY, 353, 500); // Dimensions of B3 paper in mm
    }

    @Override
    public Shape createCustomRectangle(double startX, double startY, double width, double height) {
        return new Rectangle2D.Double(startX, startY, width, height);
    }

    @Override
    public Shape createCircle(double startX, double startY, double radius) {
        return new Ellipse2D.Double(startX, startY, 2 * radius, 2 * radius);
    }
}