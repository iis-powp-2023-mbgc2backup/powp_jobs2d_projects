package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.Job2dDriver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionsManager {
    private Map<String, Job2dDriver> extensions;
    private List<Job2dDriver> usedExtensions;

    public ExtensionsManager() {
        extensions = new HashMap<>();
        usedExtensions = new ArrayList<>();
    }

    public List<Job2dDriver> getUsedExtensions() {
        return usedExtensions;
    }

    public void addExtension(String extensionName, Job2dDriver extension) {
        extensions.put(extensionName, extension);
    }

    public void toggleExtension(String extensionName) {
        Job2dDriver extension = extensions.get(extensionName);
        if (extension != null) {
            if (usedExtensions.contains(extension)) {
                usedExtensions.remove(extension);
            } else {
                usedExtensions.add(extension);
            }
        }
    }
}
