package com.bactrian;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultSaslConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

@Configuration
@EnableConfigurationProperties({RabbitConfig.RabbitSSLProperties.class})
public class RabbitConfig {



    @Bean(name = "myConnectionFactory")
    @Primary
    public ConnectionFactory connectionFactory(final RabbitSSLProperties config) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();

        if (config.getHost() != null) {
            connectionFactory.setHost(config.getHost());
            connectionFactory.setPort(config.getPort());

        }

        if (config.getUsername() != null) {
            connectionFactory.setUsername(config.getUsername());
        }
        if (config.getPassword() != null) {
            connectionFactory.setPassword(config.getPassword());
        }
        if (config.getVirtualHost() != null) {
            connectionFactory.setVirtualHost(config.getVirtualHost());
        }

        //return setUpSSL(connectionFactory, config);
        return connectionFactory;
    }



    @Primary
    @Configuration
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class RabbitSSLProperties extends RabbitProperties {
        private static final String DEFAULT_SSL_ALGORITHM = "TLSv1.1";

        private boolean useSSL;
        private String sslPropertiesLocation;
        private String sslAlgorithm = DEFAULT_SSL_ALGORITHM;

    }

    private ConnectionFactory setUpSSL(ConnectionFactory connectionFactory, RabbitSSLProperties props) throws Exception {
        String keyStoreName = "file:src/main/resources/myKeystore.pfx";
        String keyStorePassphrase = "blah";
        String trustStoreName = "file:src/main/resources/myTruststore.jks";
        String trustStorePassphrase = "blah";


        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource keyStore = resolver.getResource(keyStoreName);
        Resource trustStore = resolver.getResource(trustStoreName);
        char[] keyPassphrase = keyStorePassphrase.toCharArray();
        char[] trustPassphrase = trustStorePassphrase.toCharArray();

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(keyStore.getInputStream(), keyPassphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);

        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(trustStore.getInputStream(), trustPassphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);

        SSLContext context = SSLContext.getInstance(props.getSslAlgorithm());

        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        connectionFactory.setSaslConfig(DefaultSaslConfig.EXTERNAL);

        connectionFactory.useSslProtocol(context);

        return connectionFactory;


    }
}
