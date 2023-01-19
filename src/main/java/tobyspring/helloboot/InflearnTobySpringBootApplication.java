package tobyspring.helloboot;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

public class InflearnTobySpringBootApplication {

    public static void main(String[] args) {
        // ServletWebServerFactory, WebServer 추상화 객체
        ServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        WebServer webServer = tomcatServletWebServerFactory.getWebServer();

        webServer.start(); // Tomcat Servlet Container 동작
    }

}
