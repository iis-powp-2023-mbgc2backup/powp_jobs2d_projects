package edu.kis.powp.jobs2d.features.HeadUsage;

public final class HeadUsageFeature {
    private static HeadUsageManager headUsageManager = null;

    public static HeadUsageManager initHeadUsageFeature() {
        if (headUsageManager == null) {
            headUsageManager = new HeadUsageManager();
        }
        return headUsageManager;
    }
}
