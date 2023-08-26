package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.StandardShapeFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class CanvasManagerWindow extends JFrame implements WindowComponent {
    private HashMap<String, Dimension> paperFormatMap;
    private JPanel panel = new JPanel();
    private GridLayout layout = new GridLayout(0, 2, GAP, GAP);

    private JLabel textOriginX = new JLabel("Origin X");
    private JLabel textOriginY = new JLabel("Origin Y");
    private JLabel textWidth = new JLabel("Width");
    private JLabel textHeight = new JLabel("Height");
    private JLabel textRadius = new JLabel("Radius");
    private JLabel textPaperSize = new JLabel("Paper Size");
    private JLabel textOrientation = new JLabel("Orientation");

    private JRadioButton radioRectangle = new JRadioButton("Rectangle");
    private JRadioButton radioCircle = new JRadioButton("Circle");
    private ButtonGroup groupShape = new ButtonGroup();

    private JTextField fieldWidth = new JTextField("0");
    private JTextField fieldHeight = new JTextField("0");
    private JTextField fieldOriginX = new JTextField("0");
    private JTextField fieldOriginY = new JTextField("0");
    private JTextField fieldRadius = new JTextField("0");

    private JButton applyPaperSize = new JButton("Apply");
    private JButton btnSwapDimensions = new JButton("Swap Dimensions");
    private JButton applyCustomSize = new JButton("Apply");
    private JButton addAsCommand = new JButton("Add Canva to Commands");
    private final StandardShapeFactory shapeFactory = new StandardShapeFactory();
    private static int BORDER = 10;
    private static int GAP = 10;

    JComboBox<Object> comboPaperSize;

    public CanvasManagerWindow() {
        initPaperFormatMap();
        setTitle("Canvas Manager");
        setMinimumSize(new Dimension(360, 420));
        setLocationRelativeTo(null);

        panel.setLayout(layout);
        panel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
        add(panel);
        comboPaperSize = new JComboBox<>(paperFormatMap.keySet().toArray());

        JComboBox<Object> orientationComboBox = new JComboBox<>(new String[]{"Vertical", "Horizontal"});

        panel.add(radioRectangle);
        panel.add(radioCircle);
        groupShape.add(radioRectangle);
        groupShape.add(radioCircle);

        panel.add(textOriginX);
        panel.add(fieldOriginX);
        panel.add(textOriginY);
        panel.add(fieldOriginY);

        panel.add(textWidth);
        panel.add(fieldWidth);
        panel.add(textHeight);
        panel.add(fieldHeight);
        panel.add(textRadius);
        panel.add(fieldRadius);

        panel.add(btnSwapDimensions);
        panel.add(applyCustomSize);

        panel.add(textPaperSize);
        panel.add(comboPaperSize);
        panel.add(textOrientation);
        panel.add(orientationComboBox);
        panel.add(applyPaperSize);
        panel.add(addAsCommand);

        radioRectangle.setSelected(true);
        fieldRadius.setEnabled(false);

        radioRectangle.addActionListener(e -> {
            fieldHeight.setEnabled(true);
            fieldWidth.setEnabled(true);
            btnSwapDimensions.setEnabled(true);
            fieldRadius.setEnabled(false);
        });

        radioCircle.addActionListener(e -> {
            fieldHeight.setEnabled(false);
            fieldWidth.setEnabled(false);
            btnSwapDimensions.setEnabled(false);
            fieldRadius.setEnabled(true);
        });


        addAsCommand.addActionListener(e -> {
            CommandManager manager = CommandsFeature.getDriverCommandManager();
            ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder("Canva");
            builder.addCommands(CanvasFeature.getDrawCanvaCommands());
            manager.setCurrentCommand(builder.build());
            addAsCommand.setEnabled(false);
        });

        pack();

        applyCustomSize.addActionListener(e -> {
            if (radioRectangle.isSelected()) {
                try {
                    Shape rectangle = shapeFactory.createCustomRectangle(
                            Double.parseDouble(fieldOriginX.getText().replace(",", ".")),
                            Double.parseDouble(fieldOriginY.getText().replace(",", ".")),
                            Double.parseDouble(fieldWidth.getText().replace(",", ".")),
                            Double.parseDouble(fieldHeight.getText().replace(",", "."))
                    );

                    CanvasFeature.setShape(rectangle);
                } catch (Exception ignored) {
                }


            } else if (radioCircle.isSelected()) {
                try {
                    Shape circle = shapeFactory.createCircle(
                            Double.parseDouble(fieldOriginX.getText().replace(",", ".")),
                            Double.parseDouble(fieldOriginY.getText().replace(",", ".")),
                            Double.parseDouble(fieldRadius.getText().replace(",", "."))
                    );

                    CanvasFeature.setShape(circle);

                } catch (Exception ignored) {
                }
            }
            addAsCommand.setEnabled(true);
        });

        btnSwapDimensions.addActionListener(e -> {
            String temp = fieldWidth.getText();
            fieldWidth.setText(fieldHeight.getText());
            fieldHeight.setText(temp);
        });

        applyPaperSize.addActionListener(e -> {
            Dimension dimension = paperFormatMap.get((String) comboPaperSize.getSelectedItem());
            double originX, originY;

            try {
                originX = Double.parseDouble(fieldOriginX.getText().replace(",", "."));
                originY = Double.parseDouble(fieldOriginY.getText().replace(",", "."));
            } catch (NumberFormatException ignored) {
                return;
            }

            if (Objects.equals(orientationComboBox.getSelectedItem(), "Vertical")) {
                CanvasFeature.setShape(shapeFactory.createCustomRectangle(
                        originX, originY,
                        dimension.width, dimension.height
                ));
            } else {
                CanvasFeature.setShape(shapeFactory.createCustomRectangle(
                        originX, originY,
                        dimension.height, dimension.width
                ));
            }
            addAsCommand.setEnabled(true);
        });

    }

    private void initPaperFormatMap() {
        paperFormatMap = new HashMap<>();
        paperFormatMap.put("A1", new Dimension(594, 841));
        paperFormatMap.put("A2", new Dimension(420, 594));
        paperFormatMap.put("A3", new Dimension(297, 420));
        paperFormatMap.put("A4", new Dimension(210, 297));
        paperFormatMap.put("A5", new Dimension(148, 210));
        paperFormatMap.put("A6", new Dimension(105, 148));
    }


    @Override
    public void HideIfVisibleAndShowIfHidden() {
        this.setVisible(!this.isVisible());
    }
}
