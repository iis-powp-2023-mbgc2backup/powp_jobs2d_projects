package edu.kis.powp.jobs2d.command.gui;

public class TransformationParserFactory {
    static public TransformationParser getParser(String type) {
        switch (type) {
            case "flip":
                return new FlipTransformationParser();
            case "rotation":
                return new RotationTransformationParser();
            case "scale":
                return new ScaleTransformationParser();
            case "move":
                return new MoveTransformationParser();
            default:
                return null;
        }
    }
}
