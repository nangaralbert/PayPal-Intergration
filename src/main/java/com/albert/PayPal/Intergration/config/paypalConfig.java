package com.albert.PayPal.Intergration.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.paypal.base.rest.*;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class paypalConfig {
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public Map<String, String> payPalSDKConfig(){
        Map<String, String> config = new HashMap<String, String>();
        config.put("mode",mode);
        return config;
    }

    @Bean
    public OAuthTokenCredential  oAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,payPalSDKConfig());
    }

    @Bean
    public APIContext apiContext() throws PayPalRESTException{
        APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
        context.setConfigurationMap(payPalSDKConfig());
        return context;
    }

}