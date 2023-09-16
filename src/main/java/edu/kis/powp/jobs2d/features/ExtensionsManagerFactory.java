package edu.kis.powp.jobs2d.features;

import java.util.List;

import edu.kis.powp.jobs2d.drivers.composite.DriverComposite;

public class ExtensionsManagerFactory  {
    public static ExtensionsManager createExtensionsManager(DriverComposite composite, List<DeviceUsageManager> deviceUsageManagers) {
        ExtensionsManager extensionsManager = new ExtensionsManager();
        extensionsManager.setDriverComposite(composite);
        extensionsManager.setDeviceUsageManagers(deviceUsageManagers);
        return extensionsManager;
    }
}
