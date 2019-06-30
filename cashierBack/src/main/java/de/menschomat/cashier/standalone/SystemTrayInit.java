package de.menschomat.cashier.standalone;

import de.menschomat.cashier.configuration.BrowserInit;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Configuration
public class SystemTrayInit {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        SystemTray.SWING_UI = new CustomSwingUI();
        SystemTray systemTray = SystemTray.get();
        if (systemTray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }
        URL logo = SystemTrayInit.class.getClassLoader().getResource("tray/logo.png");
        systemTray.setImage(Objects.requireNonNull(logo));

        systemTray.setStatus("Cashier");

        systemTray.getMenu().add(new MenuItem("Open", new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

                try {
                    BrowserInit.openBrowser("http://localhost:" + environment.getProperty("local.server.port"));
                } catch (URISyntaxException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        })).setShortcut('o');

        systemTray.getMenu().add(new MenuItem("Quit", new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                systemTray.shutdown();
                System.exit(0);
            }
        })).setShortcut('q');

    }
}
