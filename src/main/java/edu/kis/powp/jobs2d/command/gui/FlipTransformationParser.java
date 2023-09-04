package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.transformations.Flip;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.Objects;

public class FlipTransformationParser implements TransformationParser{

    @Override
    public Transformation parse(String[] input) {
        if (input.length != 3) {
            System.out.println("Wrong input");
            return null;
        }
        if(Objects.equals(input[0], "flip")){
            return new Flip(Integer.parseInt(input[1]), Integer.parseInt(input[2]));
        }
        return null;
    }
}
