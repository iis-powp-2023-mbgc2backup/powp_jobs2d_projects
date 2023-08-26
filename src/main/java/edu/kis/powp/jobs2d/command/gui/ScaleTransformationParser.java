package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.transformations.Scale;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.Objects;

public class ScaleTransformationParser implements TransformationParser{

    @Override
    public Transformation parse(String[] input) {
        if (input.length != 2) {
            System.out.println("Wrong input");
            return null;
        }
        if(Objects.equals(input[0], "scale")){
            return new Scale(Integer.parseInt(input[1]));
        }
        return null;
    }
}
