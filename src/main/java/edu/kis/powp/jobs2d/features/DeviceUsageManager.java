package edu.kis.powp.jobs2d.features;

import edu.kis.powp.observer.Publisher;
import edu.kis.powp.observer.Subscriber;

public class DeviceUsageManager{
    private double headDistance = 0;
    private double operatingDistance = 0;

    private int xLastPosition = 0;
    private int yLastPosition = 0;

    private final Publisher distanceChangePublisher = new Publisher();
    private static DeviceUsageManager instance = new DeviceUsageManager();
    private boolean isLoggerObserverActive = true;


    public static DeviceUsageManager getInstance() {
        return instance;
    }

    private double calculateDistance(int x, int y){
        double distance = Math.sqrt(Math.pow(y - yLastPosition, 2) + Math.pow(x - xLastPosition, 2));
        xLastPosition = x;
        yLastPosition = y;
        return distance;
    }

    public void calculateMovingDistance(int x, int y){
        headDistance += calculateDistance(x, y);
        if (isLoggerObserverActive) {
            distanceChangePublisher.notifyObservers();
        }
    }

    public void calculateOperatingDistance(int x, int y){
        double distance = calculateDistance(x, y);
        headDistance += distance;
        operatingDistance += distance;
        if (isLoggerObserverActive) {
            distanceChangePublisher.notifyObservers();
        }
    }

    public double getHeadDistance() {
        return headDistance;
    }

    public double getOperatingDistance() {
        return operatingDistance;
    }

    public Publisher getDistanceChangePublisher() {
        return distanceChangePublisher;
    }
    public void addObserver(Subscriber observer) {
        distanceChangePublisher.addSubscriber(observer);
    }

    public void toggleLoggerObserver() {
        isLoggerObserverActive = !isLoggerObserverActive;
    }

}
