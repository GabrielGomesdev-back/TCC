package br.com.api.youspeaking.config;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ServerSetup {
    @Value("${server.http.port}")
    int ajpPort;
    
    boolean tomcatAjpEnabled = true;

    String ajpAddress = "0.0.0.0";
   
    private static final String AJP_PROTOCOL = "org.apache.coyote.http11.Http11NioProtocol";

    @Bean
    public WebServerFactoryCustomizer<AbstractServletWebServerFactory> servletContainer() { //TomcatServletWebServerFactory
      return server -> {
        if (server instanceof TomcatServletWebServerFactory) {
        if (tomcatAjpEnabled) {
        ((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(redirectConnector());
        }
        }
      };
    }

    private Connector redirectConnector() {
       Connector connector = new Connector(AJP_PROTOCOL);
       connector.setPort(ajpPort);
       connector.setSecure(false);
       connector.setAllowTrace(false);
       connector.setScheme("http");
       connector.setProperty("address", ajpAddress);
       ((AbstractAjpProtocol) connector.getProtocolHandler()).setSecretRequired(false);
       return connector;
    }

}
