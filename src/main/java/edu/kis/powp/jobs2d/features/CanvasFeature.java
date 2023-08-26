package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CanvasFeature {
    private static Shape shape;

    private static List<DriverCommand> drawCanvaCommands;

    public static Shape getShape() {
        return shape;
    }

    public static void setShape(Shape shape) {
        CanvasFeature.shape = shape;
        CanvasFeature.drawCanvaCommands = convertShapetoCommands(shape.toString());
    }

    public static List<DriverCommand> getDrawCanvaCommands() {
        return drawCanvaCommands;
    }

    private static List<DriverCommand> convertShapetoCommands(String shape) {
        Pattern decimalNumPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        Matcher matcher = decimalNumPattern.matcher(shape);

        List<Integer> canvaParameters = new ArrayList<>();
        while (matcher.find()) {
            canvaParameters.add((int) Math.round(Double.parseDouble(matcher.group())));
        }
        canvaParameters.remove(0);//remove unused argument
        //canvaParameters = [x, y, width, height]
        List<DriverCommand> commands = new ArrayList<>();
        int x = canvaParameters.get(0);
        int y = canvaParameters.get(1);
        int width = canvaParameters.get(2);
        int height = canvaParameters.get(3);
        commands.add(new SetPositionCommand(x, y));
        commands.add(new OperateToCommand(x, y + height));
        commands.add(new OperateToCommand(x + width, y + height));
        commands.add(new OperateToCommand(x + width, y));
        commands.add(new OperateToCommand(x, y));
        return commands;
    }

}
