package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.StandardShapeFactory;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.HistoryOfUsedCommandsWindow;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsManager;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsSubscriber;
import edu.kis.powp.jobs2d.command.manager.LoggerDistanceObserver;
import edu.kis.powp.jobs2d.drivers.PositionLoggingDriver;
import edu.kis.powp.jobs2d.drivers.MouseDrawerListener;
import edu.kis.powp.jobs2d.drivers.composite.DriverComposite;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.decorator.DistanceCountingDriver;
import edu.kis.powp.jobs2d.drivers.decorator.DriverDecorator;
import edu.kis.powp.jobs2d.drivers.decorator.RealWorldDriver;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     * 
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
                DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
                DriverFeature.getDriverManager());

        application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
    }

    /**
     * Setup test using driver commands in context.
     * 
     * @param application Application context.
     */
    private static void setupCommandTests(Application application) {
        application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());
        application.addTest("Load recorded command", new SelectLoadRecordedCommandOptionListener());


        application.addTest("Visitor Test", new SelectVisitorTestOptionListener());
        application.addTest("CanvasVisitor Test",new SelectCanvaVisitorTestOptionListener(new StandardShapeFactory().createCustomRectangle(0,0,537,455)));
        application.addTest("Transformation Visitor Test (Scale and Rotate)", new SelectTransformationVisitorTestOptionListener());

        application.addTest("Load immutable complex command test", new SelectTestImmutableComplexCommand(DriverFeature.getDriverManager()));

        application.addTest("Rotate left command", new SelectTransformRotateLeftVisitorOptionListener());

        application.addTest("Rotate right command", new SelectTransformRotateRightVisitorOptionListener());

        application.addTest("Scale x0.5 command", new SelectTransformScaleDownVisitorOptionListener());

        application.addTest("Scale x2 command", new SelectTransformScaleUpVisitorOptionListener());
    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     * 
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        DriverComposite composite = new DriverComposite();
        Job2dDriver loggerDriver = new PositionLoggingDriver();
        List<DeviceUsageManager> deviceUsageManagers = new ArrayList<>();
        DriverFeature.addDriver("Logger driver", loggerDriver);
        composite.add(loggerDriver);

        DeviceUsageManager deviceUsageManager;

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        DistanceCountingDriver driver = new DistanceCountingDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        deviceUsageManager = driver.getDeviceUsageManager();
        DriverFeature.addDriver("Line Simulator", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);
        deviceUsageManagers.add(deviceUsageManager);
        composite.add(driver);

        driver = new DistanceCountingDriver(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"));

        deviceUsageManager = driver.getDeviceUsageManager();
        DriverFeature.addDriver("Special line Simulator", driver);
        composite.add(driver);

        DriverDecorator verticalFlipDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getHorizontalFlip());
        deviceUsageManager = verticalFlipDriver.getDeviceUsageManager();
        DriverFeature.addDriver("Vertical flip driver", verticalFlipDriver);
        deviceUsageManagers.add(deviceUsageManager);
        composite.add(verticalFlipDriver);

        DriverDecorator horizontalFlipDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getVerticalFlip());
        deviceUsageManager = horizontalFlipDriver.getDeviceUsageManager();
        DriverFeature.addDriver("Horizontal flip driver", horizontalFlipDriver);
        deviceUsageManagers.add(deviceUsageManager);
        composite.add(horizontalFlipDriver);

        DriverDecorator halfScaleDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getHalfScale());
        deviceUsageManager = halfScaleDriver.getDeviceUsageManager();
        DriverFeature.addDriver("Half scale driver", halfScaleDriver);
        deviceUsageManagers.add(deviceUsageManager);
        composite.add(halfScaleDriver);

        DriverDecorator doubleScaleDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getDoubleScale());
        deviceUsageManager = doubleScaleDriver.getDeviceUsageManager();
        deviceUsageManagers.add(deviceUsageManager);
        DriverFeature.addDriver("Double scale driver", doubleScaleDriver);
        composite.add(doubleScaleDriver);

        DriverDecorator clockwiseRotationDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getClockwiseRotation());
        deviceUsageManager = clockwiseRotationDriver.getDeviceUsageManager();
        deviceUsageManagers.add(deviceUsageManager);
        DriverFeature.addDriver("Clockwise rotation driver", clockwiseRotationDriver);
        composite.add(clockwiseRotationDriver);

        DriverDecorator counterClockwiseRotationDriver = new TransformationDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), TransformationFactory.getCounterclockwiseRotation());
        deviceUsageManager = counterClockwiseRotationDriver.getDeviceUsageManager();
        deviceUsageManagers.add(deviceUsageManager);
        DriverFeature.addDriver("Counterclockwise rotation Driver", counterClockwiseRotationDriver);
        composite.add(counterClockwiseRotationDriver);

        Job2dDriver realWorldDriver = new RealWorldDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), 50);
        DriverFeature.addDriver("Real world Driver", realWorldDriver);
        composite.add(realWorldDriver);

        DriverFeature.addDriver("Line driver", composite);

        ExtensionsManager extensionsManager = ExtensionsManagerFactory.createExtensionsManager(composite, deviceUsageManagers);
        extensionsManager.setup(application);

        DriverFeature.updateDriverInfo();
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
        HistoryOfUsedCommandsManager historyOfUsedCommandsManager = new HistoryOfUsedCommandsManager();
        historyOfUsedCommandsManager.setCommandManager(CommandsFeature.getDriverCommandManager());

        HistoryOfUsedCommandsWindow historyOfUsedCommandsWindow = new HistoryOfUsedCommandsWindow(historyOfUsedCommandsManager);
        HistoryOfUsedCommandsSubscriber historyOfUsedCommandsSubscriber = new HistoryOfUsedCommandsSubscriber(historyOfUsedCommandsWindow);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(historyOfUsedCommandsSubscriber);
        application.addWindowComponent("History of used commands", historyOfUsedCommandsWindow);
    }

    /**
     * Setup menu for adjusting logging settings.
     * 
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                FeatureManager.add(DrawerFeature.class);
                FeatureManager.add(TransformationsFeature.class);
                FeatureManager.add(DriverFeature.class);
                FeatureManager.add(RecordFeature.class);
                FeatureManager.add(CommandsFeature.class);
                FeatureManager.setupFeatures(app);

                setupDrivers(app);
                setupPresetTests(app);
                setupCommandTests(app);
                setupLogger(app);
                setupWindows(app);

                app.setVisibility(true);
                app.getFreePanel().addMouseListener(
                        new MouseDrawerListener(DriverFeature.getDriverManager(),
                                app.getFreePanel().getWidth(),
                                app.getFreePanel().getHeight())
                );
            }
        });
    }

}
