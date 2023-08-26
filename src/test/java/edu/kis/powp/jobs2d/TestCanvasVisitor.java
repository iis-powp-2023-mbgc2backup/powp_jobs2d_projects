package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TestCanvasVisitor {
    public static void main(String[] args) {
        ShapeFactory factory = new StandardShapeFactory();
        Shape customRectangle = factory.createCustomRectangle(0, 0, 100, 200);
        Shape circle = factory.createCircle(40, 40, 50);

        SetPositionCommand setPositionCommand = new SetPositionCommand(0, 0);
        OperateToCommand operateToCommand1 = new OperateToCommand(10, 0);
        OperateToCommand operateToCommand2 = new OperateToCommand(10, 1000);
        OperateToCommand operateToCommand3 = new OperateToCommand(0, 10);
        OperateToCommand operateToCommand4 = new OperateToCommand(0, 0);

        List<DriverCommand> commands =  Arrays.asList(setPositionCommand, operateToCommand1, operateToCommand2, operateToCommand3, operateToCommand4);
        CanvasVisitor visitor = new CanvasVisitor(customRectangle);

        for (DriverCommand command : commands) {
            try{
                command.accept(visitor);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }

        OperateToCommand pointInside1 = new OperateToCommand(90, 90);
        OperateToCommand pointInside2 = new OperateToCommand(120, 90);
        OperateToCommand pointInside3 = new OperateToCommand(90, 120);
        OperateToCommand pointInside4 = new OperateToCommand(60, 90);
        OperateToCommand pointInside5 = new OperateToCommand(90, 60);

        OperateToCommand pointOutside1 = new OperateToCommand(150, 150);
        OperateToCommand pointOutside2 = new OperateToCommand(150, 90);
        OperateToCommand pointOutside3 = new OperateToCommand(90, 150);
        OperateToCommand pointOutside4 = new OperateToCommand(30, 90);
        OperateToCommand pointOutside5 = new OperateToCommand(90, 30);
        List<DriverCommand> commandsCircle =  Arrays.asList(pointInside1,pointInside2,pointInside3,pointInside4,pointInside5,pointOutside1,pointOutside2,pointOutside3,pointOutside4,pointOutside5);
        visitor.setCanvas(circle);

        for (DriverCommand command : commandsCircle) {
            try{
                command.accept(visitor);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
