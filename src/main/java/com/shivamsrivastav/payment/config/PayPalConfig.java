package com.shivamsrivastav.payment.config;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * PayPal Configuration
 * 
 * Sets up the PayPal API Context with credentials from application.yml.
 * 
 * @author Shivam Srivastav
 */
@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    /**
     * Creates the PayPal API Context.
     * This context is used for all PayPal API calls.
     */
    @Bean
    public APIContext apiContext() throws PayPalRESTException {
        // Create API Context with client ID and secret
        APIContext context = new APIContext(clientId, clientSecret, mode);

        // Optional: Configuration map
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", mode);
        context.setConfigurationMap(configMap);

        return context;
    }
}
