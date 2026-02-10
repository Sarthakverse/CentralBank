package org.sarthak.accounts.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileStartupLogger implements ApplicationRunner {
    private final Environment environment;

    public ProfileStartupLogger(Environment environment) {
        this.environment = environment;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            log.info("ACTIVE PROFILE : DEFAULT");
        } else {
            log.info("Active profiles: {}", String.join(", ", activeProfiles));
        }
    }
}
