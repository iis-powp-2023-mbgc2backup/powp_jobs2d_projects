package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.transformations.Move;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.Objects;

public class MoveTransformationParser implements TransformationParser{
    @Override
    public Transformation parse(String[] input) {
        if (input.length != 3) {
            System.out.println("Wrong input");
            return null;
        }
        if(Objects.equals(input[0], "move")){
            return new Move(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
        }
        return null;
    }
}
