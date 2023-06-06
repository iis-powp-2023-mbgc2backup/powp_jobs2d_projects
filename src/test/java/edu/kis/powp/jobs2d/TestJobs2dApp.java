package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.HistoryOfUsedCommandsWindow;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsManager;
import edu.kis.powp.jobs2d.command.manager.HistoryOfUsedCommandsSubscriber;
import edu.kis.powp.jobs2d.command.manager.LoggerDistanceObserver;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.PositionLoggingDriver;
import edu.kis.powp.jobs2d.drivers.MouseDrawerListener;
import edu.kis.powp.jobs2d.drivers.DriverComposite;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.decorator.DistanceCountingDriver;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.kis.powp.jobs2d.features.AdditionalFeatures.setupAdditionalFeaturesPlugin;

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

        application.addTest("Load immutable complex command test", new SelectTestImmutableComplexCommand(DriverFeature.getDriverManager()));

        application.addTest("CommandTransformVisitor test", new SelectTransformVisitorOptionListener());

    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     * 
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        Job2dDriver basicLine = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("Basic Line", basicLine);
        Job2dDriver specialLine = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special Line", specialLine);
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
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();
                TransformationsFeature.setupTransformationPlugin(app);

                DriverFeature.setupDriverPlugin(app);
                RecordFeature.setupRecorderPlugin(app);
                setupDrivers(app);
                setupPresetTests(app);
                setupCommandTests(app);
                setupLogger(app);
                setupWindows(app);
                setupAdditionalFeaturesPlugin(app);
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
