package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.transformations.Rotation;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.Objects;

public class RotationTransformationParser implements TransformationParser{

    @Override
    public Transformation parse(String[] input) {
        if (input.length != 3) {
            System.out.println("Wrong input");
            return null;
        }
        if(Objects.equals(input[0], "rotation")){
            return new Rotation(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
        }
        return null;
    }
}
