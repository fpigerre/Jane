package io.github.psgs.jane.interfaces;

import io.github.psgs.jane.Jane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemTrayIcon {

    private TrayIcon trayIcon;

    public SystemTrayIcon() {
        initiateTrayIcon();
    }

    private void initiateTrayIcon() {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem item1 = new MenuItem("Open");
        MenuItem item2 = new MenuItem("Exit");

        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        try {
            popupMenu.add(item1);
            popupMenu.add(item2);
            Image img = Toolkit.getDefaultToolkit().getImage("src/resources/largeicon.png");
            trayIcon = new TrayIcon(img, "Jane", popupMenu);
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException ex) {

        }
    }

    public TrayIcon getTrayIcon() {
        return this.trayIcon;
    }

    public void openActionPerformed(ActionEvent event) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }

    public void exitActionPerformed(ActionEvent event) {
        Jane.exit();
    }
}
