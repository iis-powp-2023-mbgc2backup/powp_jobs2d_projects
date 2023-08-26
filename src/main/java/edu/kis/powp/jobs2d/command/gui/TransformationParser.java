package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.jobs2d.transformations.Transformation;

public interface TransformationParser {
    Transformation parse(String[] input);
}