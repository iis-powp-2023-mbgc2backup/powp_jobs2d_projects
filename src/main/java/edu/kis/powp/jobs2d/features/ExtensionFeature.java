package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.events.SelectListener;


public class ExtensionFeature {
    private static ExtensionsManager extensionsManager =  new ExtensionsManager();
	private static Application app;

	public static ExtensionsManager getExtensionsManager(){
		return extensionsManager;
	}

	public static void setUpExtensionFeature(Application application){
		app = application;
		app.addComponentMenu(ExtensionFeature.class,"Extensions");
	}

	public static void addExtension(String name, Job2dDriver driver){
		SelectListener listener = new SelectListener(extensionsManager);
		app.addComponentMenuElementWithCheckBox(ExtensionFeature.class, name, listener,false);
		addExtensionToList(name, driver);
	}

	private static void addExtensionToList(String name, Job2dDriver job2dDriver){
		extensionsManager.addExtension(name, job2dDriver);
	}
}
