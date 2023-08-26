package edu.kis.powp.jobs2d.command.gui;


import edu.kis.powp.jobs2d.transformations.Transformation;

public abstract class CommandTransformationParser{
    private static Transformation transformation;

    public static Transformation parseText(String input) {
        String[] transformArray = input.split(" ");
        TransformationParser parser = TransformationParserFactory.getParser(transformArray[0]);
        if (parser != null) {
            try {
                transformation = parser.parse(transformArray);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input");
            }
        } else {
            System.out.println("Wrong input");
        }
        return transformation;
    }
}

