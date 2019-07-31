package de.menschomat.cashier.configuration;


import de.menschomat.cashier.security.JWTAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Configuration
@Transactional
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @PostConstruct
    private void init() {
        logger.info("DEBUGMODE:" + logger.isDebugEnabled());
        logger.info("WARNMODE:" + logger.isWarnEnabled());
    }

}
