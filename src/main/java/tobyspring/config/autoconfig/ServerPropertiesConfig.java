package tobyspring.config.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import tobyspring.config.MyAutoConfiguration;

@MyAutoConfiguration
public class ServerPropertiesConfig {

    @Bean
    public ServerProperties serverProperties(Environment env) {
        ServerProperties serverProperties = new ServerProperties();
        serverProperties.setPort(Integer.parseInt(env.getProperty("port")));
        serverProperties.setContextPath(env.getProperty("contextPath"));
        return serverProperties;
    }

}
