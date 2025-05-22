package br.com.clientecredentials.client_server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpRequest;

@Configuration
public class RestClientConfig {
  @Bean
  RestClient keycloakRestClient(RestClient.Builder builder,
      OAuth2AuthorizedClientManager auth2AuthorizedClientManager) {
    var requestInterceptor = new OAuth2ClientHttpRequestInterceptor(auth2AuthorizedClientManager);
    requestInterceptor.setClientRegistrationIdResolver((HttpRequest request) -> "client-service");

    return builder.requestInterceptor(requestInterceptor).build();
  }
}
