package de.menschomat.cashier;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

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


    public static void main(String[] args) throws IOException {
        disableAccessWarnings();
        java.io.InputStream is = CashierApp.class.getClassLoader().getResourceAsStream("application.properties");
        File file = new File("application.properties");
        java.util.Properties p = new Properties();
        if (file.exists())
            is = new FileInputStream(file);
        p.load(is);
        if (p.getProperty("standAlone.active").equals("true")) {
            System.setProperty("apple.awt.UIElement", "true");
            new SpringApplicationBuilder(CashierApp.class).headless(false).run(args);
        } else {
            new SpringApplicationBuilder(CashierApp.class).run(args);
        }
    }
}

