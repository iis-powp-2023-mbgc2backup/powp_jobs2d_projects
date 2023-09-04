package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private final static List<Class<? extends FeatureObject>> featureList = new ArrayList<>();

    public static void add(Class<? extends FeatureObject> featureClass) {
        featureList.add(featureClass);
    }

    public static void add(List<Class<? extends FeatureObject>> featureClasses) {
        featureList.addAll(featureClasses);
    }

    public static List<Class<? extends FeatureObject>> getList() {
        return featureList;
    }
    public static Class<? extends FeatureObject> get(int index) {
        if (index < 0 || index >= featureList.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return featureList.get(index);
    }

    public static void setupFeatures(Application application) {
        for (Class<? extends FeatureObject> feature : featureList) {
            try {
                FeatureObject featureInstance = feature.getDeclaredConstructor().newInstance();
                featureInstance.setup(application);
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}