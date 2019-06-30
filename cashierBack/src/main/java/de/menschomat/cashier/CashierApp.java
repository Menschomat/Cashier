package de.menschomat.cashier;


import com.apple.eawt.Application;
import de.menschomat.cashier.configuration.BrowserInit;
import de.menschomat.cashier.standalone.CustomSwingUI;
import de.menschomat.cashier.standalone.SystemTrayInit;
import dorkbox.systemTray.SystemTray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;


@SpringBootApplication
@EnableJpaAuditing
public class CashierApp {
    @SuppressWarnings("unchecked")
    public static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }


    public static void main(String[] args) {
        disableAccessWarnings();
        System.setProperty("apple.awt.UIElement", "true");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CashierApp.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }
}
