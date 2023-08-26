package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.Bookmarks;
import edu.kis.powp.jobs2d.command.gui.BookmarksWindow;
import edu.kis.powp.jobs2d.command.gui.CanvasManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandEditWindow;
import edu.kis.powp.jobs2d.command.gui.CommandEditorWindow;
import edu.kis.powp.jobs2d.command.gui.CommandEditorWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.HistoryOfUsedCommandsWindow;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsManager;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsSubscriber;
import edu.kis.powp.jobs2d.command.manager.LoggerDistanceObserver;
import edu.kis.powp.jobs2d.drivers.DriverComposite;
import edu.kis.powp.jobs2d.drivers.MouseDrawerListener;
import edu.kis.powp.jobs2d.drivers.PositionLoggingDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.decorator.DistanceCountingDriver;
import edu.kis.powp.jobs2d.drivers.decorator.RecordingDriver;
import edu.kis.powp.jobs2d.drivers.decorator.RealWorldDriver;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.HeadUsage.HeadUsageFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;

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
        application.addTest("CanvasVisitor Test", new SelectCanvaVisitorTestOptionListener());
        application.addTest("Transformation Visitor Test (Scale and Rotate)",
                new SelectTransformationVisitorTestOptionListener());

        application.addTest("Load immutable complex command test",
                new SelectTestImmutableComplexCommand(DriverFeature.getDriverManager()));

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
        DriverFeature.addDriver("Logger driver", loggerDriver);
        composite.addDriver(loggerDriver);

        DeviceUsageManager deviceUsageManager;

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        DistanceCountingDriver driver = new DistanceCountingDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        deviceUsageManager = driver.getDeviceUsageManager();
        deviceUsageManager.getDistanceChangePublisher().addSubscriber(new LoggerDistanceObserver(deviceUsageManager));
        DriverFeature.addDriver("Line Simulator + distance log", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);
        composite.addDriver(driver);
        
        driver = new DistanceCountingDriver(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"));


        deviceUsageManager = driver.getDeviceUsageManager();
        deviceUsageManager.getDistanceChangePublisher().addSubscriber(new LoggerDistanceObserver(deviceUsageManager));
        DriverFeature.addDriver("Special line Simulator + distance log", driver);
        DriverFeature.addDriver("Logger + line driver + distance log", composite);

        Job2dDriver verticalFlipDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getHorizontalFlip());
        DriverFeature.addDriver("Vertical flip driver", verticalFlipDriver);

        Job2dDriver horizontalFlipDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getVerticalFlip());
        DriverFeature.addDriver("Horizontal flip driver", horizontalFlipDriver);

        Job2dDriver halfScaleDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getHalfScale());
        DriverFeature.addDriver("Half scale driver", halfScaleDriver);

        Job2dDriver doubleScaleDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getDoubleScale());
        DriverFeature.addDriver("Double scale driver", doubleScaleDriver);

        Job2dDriver clockwiseRotationDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getClockwiseRotation());
        DriverFeature.addDriver("Clockwise rotation driver", clockwiseRotationDriver);

        Job2dDriver counterClockwiseRotationDriver = new TransformationDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"),
                TransformationFactory.getCounterclockwiseRotation());
        DriverFeature.addDriver("Counterclockwise rotation Driver", counterClockwiseRotationDriver);

        driver = new DistanceCountingDriver(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"));
        DriverFeature.addDriver("Special line Simulator", driver);
        DriverFeature.addDriver("Logger + line driver", composite);

        composite.addDriver(HeadUsageFeature.initHeadUsageFeature());

 
        Job2dDriver realWorldDriver = new RealWorldDriver(
                new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), 50);
        DriverFeature.addDriver("Real world Driver", realWorldDriver);
 
        DriverFeature.updateDriverInfo();
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandEditWindow commandEditor = new CommandEditWindow(CommandsFeature.getDriverCommandManager());
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(commandEditor);
        application.addWindowComponent("Command Editor", commandEditor);

        CommandEditorWindow commandEditor2 = new CommandEditorWindow(CommandsFeature.getDriverCommandManager());
        application.addWindowComponent("Command Editor 2", commandEditor2);

        CommandEditorWindowCommandChangeObserver editorWindowObserver = new CommandEditorWindowCommandChangeObserver(
                commandEditor2);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(editorWindowObserver);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(windowObserver);
        HistoryOfUsedCommandsManager historyOfUsedCommandsManager = new HistoryOfUsedCommandsManager();
        historyOfUsedCommandsManager.setCommandManager(CommandsFeature.getDriverCommandManager());

        HistoryOfUsedCommandsWindow historyOfUsedCommandsWindow = new HistoryOfUsedCommandsWindow(
                historyOfUsedCommandsManager);
        HistoryOfUsedCommandsSubscriber historyOfUsedCommandsSubscriber = new HistoryOfUsedCommandsSubscriber(
                historyOfUsedCommandsWindow);
        CommandsFeature.getDriverCommandManager().getChangePublisher().addSubscriber(historyOfUsedCommandsSubscriber);
        application.addWindowComponent("History of used commands", historyOfUsedCommandsWindow);

        CanvasManagerWindow canvasManagerWindow = new CanvasManagerWindow();
        application.addWindowComponent("Canvas Size Manager", canvasManagerWindow);

        BookmarksWindow bookmarksWindow = new BookmarksWindow();
        application.addWindowComponent("Bookmarks", bookmarksWindow);
        Bookmarks.getInstance().setGui(bookmarksWindow);
        Bookmarks.getInstance().setCommandManager(CommandsFeature.getDriverCommandManager());
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
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
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
                app.getFreePanel().addMouseWheelListener(new MouseWheelModeFeature());
                 app.getFreePanel().addMouseListener(new MouseMovingModeFeature());

 
            }
        });
    }

}