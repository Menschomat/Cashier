package de.menschomat.wgo;

import de.menschomat.wgo.rest.model.UserSessionData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;


@SpringBootApplication
public class WgoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WgoApplication.class, args);
    }

}
