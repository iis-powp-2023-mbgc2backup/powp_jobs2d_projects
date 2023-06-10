package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.recorder.CommandRecorder;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.decorator.RecordingDriver;
import edu.kis.powp.jobs2d.events.RecordingDriverDecoratingSubscriber;
import edu.kis.powp.jobs2d.events.SelectStartRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectStopRecordingOptionListener;
import edu.kis.powp.jobs2d.events.SelectClearRecordingOptionListener;
import edu.kis.powp.observer.Subscriber;

import java.util.List;


public class RecordFeature {

    private static Application app;
    private static CommandRecorder commandRecorder;

    private static boolean isRecording = false;

    /**
     * Setup jobs2d drivers Plugin and add to application.
     *
     * @param application Application context.
     */
    public static void setupRecorderPlugin(Application application) {
        commandRecorder = new CommandRecorder();
        SelectStartRecordingOptionListener selectStartRecordingOptionListener = new SelectStartRecordingOptionListener();
        SelectStopRecordingOptionListener selectStopRecordingOptionListener = new SelectStopRecordingOptionListener();
        SelectClearRecordingOptionListener selectClearRecordingOptionListener = new SelectClearRecordingOptionListener();


        app = application;
        app.addComponentMenu(edu.kis.powp.jobs2d.features.RecordFeature.class, "Recorder");
        app.addComponentMenuElement(edu.kis.powp.jobs2d.features.RecordFeature.class, "Start Recording", selectStartRecordingOptionListener);
        app.addComponentMenuElement(edu.kis.powp.jobs2d.features.RecordFeature.class, "Stop Recording", selectStopRecordingOptionListener);
        app.addComponentMenuElement(edu.kis.powp.jobs2d.features.RecordFeature.class, "Clear Recording", selectClearRecordingOptionListener);

        DriverFeature.getDriverManager().addSubscriber(new RecordingDriverDecoratingSubscriber());
    }

    public static void recordCommand(DriverCommand command){
        if(isRecording){
            commandRecorder.addCommand(command);
        }
    }

    public static void setRecording(){
        isRecording = true;
    }

    public static void stopRecording(){
        isRecording = false;
    }

    public static void clearRecording(){
        commandRecorder.clearCommand();
    }

    public static List<DriverCommand> getCommands(){
        return commandRecorder.getCommands();
    }

}



