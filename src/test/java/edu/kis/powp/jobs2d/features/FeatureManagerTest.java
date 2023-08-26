package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.*;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class FeatureManagerTest {

    private static class MockFeatureObject implements FeatureObject {
        private boolean isSetupCalled = false;

        @Override
        public void setup(Application application) {
            isSetupCalled = true;
        }

        public boolean isSetupCalled() {
            return isSetupCalled;
        }
    }

    @Test
    public void testAddSingleFeature() {
        FeatureManager.add(MockFeatureObject.class);
        List<Class<? extends FeatureObject>> featureList = FeatureManager.getList();

        assertEquals(MockFeatureObject.class, featureList.get(0));
    }

    @Test
    public void testAddMultipleFeatures() {
        List<Class<? extends FeatureObject>> featureClasses = new ArrayList<>();
        featureClasses.add(MockFeatureObject.class);
        featureClasses.add(MockFeatureObject.class);
        featureClasses.add(MockFeatureObject.class);

        FeatureManager.add(featureClasses);
        List<Class<? extends FeatureObject>> featureList = FeatureManager.getList();

        assertEquals(3, featureList.size());
        assertEquals(MockFeatureObject.class, featureList.get(0));
        assertEquals(MockFeatureObject.class, featureList.get(1));
        assertEquals(MockFeatureObject.class, featureList.get(2));
    }

    @Test
    public void testGetFeatureByIndex() {
        FeatureManager.add(MockFeatureObject.class);
        FeatureManager.add(MockFeatureObject.class);
        FeatureManager.add(MockFeatureObject.class);

        Class<? extends FeatureObject> featureClass = FeatureManager.get(1);
        assertEquals(MockFeatureObject.class, featureClass);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFeatureByInvalidIndex() {
        int invalidIndex = 7;

        FeatureManager.get(invalidIndex);
    }


}