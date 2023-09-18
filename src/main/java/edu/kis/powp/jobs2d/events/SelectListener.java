package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.features.ExtensionsManager;

public class SelectListener implements ActionListener {
    private ExtensionsManager extensionsManager;

    public SelectListener(ExtensionsManager extensionsManager) {
        this.extensionsManager = extensionsManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        extensionsManager.toggleExtension(e.getActionCommand());
    }
}
