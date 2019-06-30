package de.menschomat.cashier.configuration.standalone;

import de.menschomat.cashier.configuration.BrowserInit;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Configuration
@ConditionalOnProperty(
        value="standAlone.active",
        havingValue = "true")
public class SystemTrayInit {

    private final Environment environment;

    public SystemTrayInit(Environment environment) {
        this.environment = environment;
    }

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

        systemTray.getMenu().add(new MenuItem("Open", e -> {

            try {
                BrowserInit.openBrowser("http://localhost:" + environment.getProperty("local.server.port"));
            } catch (URISyntaxException | IOException ex) {
                ex.printStackTrace();
            }
        })).setShortcut('o');

        systemTray.getMenu().add(new MenuItem("Quit", e -> {
            systemTray.shutdown();
            System.exit(0);
        })).setShortcut('q');

    }
}
